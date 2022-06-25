#include <Adafruit_MPU6050.h>
#include <Adafruit_Sensor.h>
#include <Wire.h>
#include <WiFi.h>
#include <Firebase_ESP_Client.h>

//Provide the token generation process info.
#include <addons/TokenHelper.h>

TaskHandle_t Task0;
bool resumeTask0 = true;

TaskHandle_t Task1;
bool resumeTask1 = true;

#define DATABASE_URL "https://sil-lab-default-rtdb.asia-southeast1.firebasedatabase.app/"
#define API_KEY "AIzaSyDYEeZfC5F7u9OgqOJw6CSaqTTo-JzGw9M"

#define USER_EMAIL "thorhammer@gmail.com"
#define USER_PASSWORD "12345678"

#define WIFI_SSID "Locked"
#define WIFI_PASSWORD "superkart"

#define BELT_ID "000000"

//Define Firebase objects
FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig conf;
bool signupOK = false;

Adafruit_MPU6050 mpu;
float x = 0, y = 0, z = 0; //MPU 6050 library
float sum_data_temp = 0, S_D = 0;
const int C_ARRAY_SIZE = 1024;
const int SEND_BUFFER_SIZE = 1024;

const int UPLOAD_LIMIT_TOP = 64;
const int COMBINE_DATA = 16;

const int READ_DELAY = 1000;
const int PROCESS_DELAY = READ_DELAY * COMBINE_DATA;

int _temp_ = 0;
int back = 0, front = 1, bottom = 0, top = 1;

sensors_event_t a, g, temp;

// Correct Location To upload data To as per database Structure
char t[] = { '/', 'c', '0', '0', '0', '0', '0', '/', '0', '0', '0', '0', '0', '0', '\0' };

bool getCowID()
{
  
  return true;
}

bool getlastT()
{
  
  return true;
}

char* getnext(int st, int lt)
{
  // TODO
  // Proper Increment
  // Proper Addressing

  //int p = 14;
  int p = lt + 1;
  //while (p > 8)
  ++t[p - 1];
  while (p > st)
  {
    --p;
    if (t[p] > '9')
    {
      t[p] -= 10;
      if (p != 0)
        ++t[p - 1];
    }
  }

  return t;
}

struct _data_
{
  int t = 0;
  float x, y, z;
  float g_x, g_y, g_z;
  float avg_acc;
} _data_[C_ARRAY_SIZE];

struct _packet_
{
  float mean_temp;
  float ppv_acc, cf_acc, st_y_acc;
  float sd_acc, mean_acc, rms_acc, max_acc, min_acc;
} _packet_[SEND_BUFFER_SIZE];

float rmsValue(float arr[])
{
  float square = 0.0;
  float mean = 0.0, root = 0.0;

  // Calculate square.
  for (int i = 0; i < 3; i++)
    square += arr[i] * arr[i];

  // Calculate Mean.
  mean = (square / 3.0);

  // Calculate Root.
  root = sqrt(mean);

  return root;
}

void storeData(void * res)
{
  Serial.print("Storing Data on Core ");
  Serial.println(xPortGetCoreID());

  while ((bool*) res)
  {
    delay(READ_DELAY);

    Serial.print("Storing Data (f,b) ");
    Serial.print(front);
    Serial.print(", ");
    Serial.println(back);

    mpu.getEvent(&a, &g, &temp);

    _temp_ = analogRead(34);
    _data_[front].x = a.acceleration.x;
    _data_[front].y = a.acceleration.y;
    _data_[front].z = a.acceleration.z;
    // gyro readings
    _data_[front].g_x = g.gyro.x;
    _data_[front].g_y = g.gyro.y;
    _data_[front].g_z = g.gyro.z;
    _data_[front].t = _temp_;
    float arr[] = {_data_[front].x, _data_[front].y, _data_[front].z};
    _data_[front].avg_acc = rmsValue(arr);
    ++front;
    if (front == C_ARRAY_SIZE)
    {
      front = 0;
    }
    if (front == back)
    { // OverFlow - Nothing can be done
      --front;
      if (front < 0)
        front = C_ARRAY_SIZE - 1;
    }
  }
}

void combineData(struct _packet_ & toStore)
{
  float sum_data = 0, sum_avg_data = 0, sum_data_y = 0;
  float sum_data_temp = 0, sum_rms_data = 0, S_D = 0;

  toStore.min_acc = _data_[back + 1].avg_acc;
  toStore.max_acc = _data_[back + 1].avg_acc;

  int last = back + COMBINE_DATA + 1;
  for (int i = back + 1; i < last; ++i)
  {
    sum_data += _data_[i].avg_acc;
    sum_data_y += _data_[i].y;
    sum_rms_data += (_data_[i].avg_acc) * (_data_[i].avg_acc);
    sum_data_temp += _data_[i].t;

    if (_data_[i].avg_acc < toStore.min_acc)
    {
      toStore.min_acc = _data_[i].avg_acc;
    }

    if (_data_[i].avg_acc > toStore.max_acc)
    {
      toStore.max_acc = _data_[i].avg_acc;
    }
  }
  toStore.ppv_acc = toStore.max_acc - toStore.min_acc;
  toStore.cf_acc = toStore.max_acc / toStore.rms_acc;
  toStore.st_y_acc = sum_data_y / COMBINE_DATA;
  toStore.rms_acc = sum_rms_data / COMBINE_DATA;      // rms acc
  toStore.mean_acc = sum_data / COMBINE_DATA;         // mean acc
  toStore.mean_temp = sum_data_temp / COMBINE_DATA; //mean temp

  for (int i = back + 1; i < last; ++i)
  {
    S_D += pow(_data_[i].avg_acc - toStore.mean_acc, 2);
  }

  toStore.sd_acc = S_D / COMBINE_DATA;
}

bool enoughReadingsExist(int f, int b, int len, int readingsNo = 1)
{
  if (f > b && f - b > readingsNo)
    return true;
  else if (f < b && len - b + f > readingsNo)
    return true;
  else if (f == b)
    return true;
  return false;
}

void processAndUploadData(void * res)
{
  Serial.print("Processing and Uploading Data on Core ");
  Serial.println(xPortGetCoreID());

  while ((bool*) res)
  {
    delay(PROCESS_DELAY);
    bool error = false;

    // Upload Data If There Exist Enough
    if (enoughReadingsExist(top, bottom, SEND_BUFFER_SIZE, UPLOAD_LIMIT_TOP))
    {
      // Wake WiFi
      WiFi.disconnect(false);  // Reconnect the network
      WiFi.mode(WIFI_STA);    // Switch WiFi on

      WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
      Serial.print("Connecting to ");
      Serial.println(WIFI_SSID);

      while (WiFi.status() != WL_CONNECTED)
      {
        Serial.print(".");
        delay(300);
      }

      Firebase.reconnectWiFi(true);
      Firebase.begin(&conf, &auth);

      // Send Available Data If Possible
      while (enoughReadingsExist(top, bottom, SEND_BUFFER_SIZE))
      {
        ++bottom;

        Serial.print("Sending Data ");
        Serial.println(bottom);

        if (bottom >= SEND_BUFFER_SIZE)
          bottom = 0;

        if (!error && !Firebase.RTDB.setFloat(&fbdo, (const char*)getnext(8, 13), _packet_[bottom].mean_temp))
          error = true;

        if (!error && !Firebase.RTDB.setFloat(&fbdo, (const char*)getnext(8, 13), _packet_[bottom].ppv_acc))
          error = true;

        if (!error && !Firebase.RTDB.setFloat(&fbdo, (const char*)getnext(8, 13), _packet_[bottom].cf_acc))
          error = true;

        if (!error && !Firebase.RTDB.setFloat(&fbdo, (const char*)getnext(8, 13), _packet_[bottom].st_y_acc))
          error = true;

        if (!error && !Firebase.RTDB.setFloat(&fbdo, (const char*)getnext(8, 13), _packet_[bottom].sd_acc))
          error = true;

        if (!error && !Firebase.RTDB.setFloat(&fbdo, (const char*)getnext(8, 13), _packet_[bottom].rms_acc))
          error = true;

        if (error)
        {
          Serial.print("Error Sending Data ");
          Serial.println(bottom);
          --bottom;
          if (bottom < 0)
            bottom = SEND_BUFFER_SIZE - 1;
          break;
        }
      }

      // Sleep WiFi
      WiFi.disconnect(true);  // Disconnect from the network
      WiFi.mode(WIFI_OFF);
    }

    if (enoughReadingsExist(front, back, C_ARRAY_SIZE, COMBINE_DATA))
    {
      Serial.print("Processing Data (t,b) ");
      Serial.print(top);
      Serial.print(", ");
      Serial.println(bottom);
      combineData(_packet_[top++]);
    }
    else
      continue;

    if (top == SEND_BUFFER_SIZE)
      top = 0;
    if (top == bottom)
    { // OverFlow - Nothing can be done
      --top;
      if (top < 0)
        top = SEND_BUFFER_SIZE - 1;
    }

    back += COMBINE_DATA;
    if (back >= C_ARRAY_SIZE)
      back -= C_ARRAY_SIZE;
  }
}

void setup()
{
  Serial.begin(115200);

  if (!mpu.begin())
  {
    Serial.println("Failed to find MPU6050 chip");
    while (1)
      delay(10);
  }
  Serial.println("MPU6050 Found!");

  mpu.setAccelerometerRange(MPU6050_RANGE_4_G);
  mpu.setGyroRange(MPU6050_RANGE_500_DEG);
  mpu.setFilterBandwidth(MPU6050_BAND_5_HZ);

  delay(100);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);

  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(500);
  }

  Serial.println();
  Serial.print("Connected");
  Serial.print("IP Address: ");
  Serial.println(WiFi.localIP());

  /* Assign the api key (required) */
  conf.api_key = API_KEY;

  /* Assign the user sign in credentials */
  auth.user.email = USER_EMAIL;
  auth.user.password = USER_PASSWORD;

  /* Assign the RTDB URL (required) */
  conf.database_url = DATABASE_URL;

  /* Sign up */
  if (Firebase.signUp(&conf, &auth, "", ""))
  {
    Serial.println("Firebase Active");
    signupOK = true;
  }
  else
    Serial.printf("%s\n", conf.signer.signupError.message.c_str());

  /* Assign the callback function for the long running token generation task */
  conf.token_status_callback = tokenStatusCallback; //see addons/TokenHelper.h

  Firebase.reconnectWiFi(true);
  Firebase.begin(&conf, &auth);

  if (!getCowID()); // Link ESP32 To Cow to get upload address
  if (!getlastT()); // Resumes From Last Saved Point

  // Modem Sleep To Save Power
  WiFi.disconnect(true);  // Disconnect from the network
  WiFi.mode(WIFI_OFF);

  xTaskCreatePinnedToCore(storeData, "SaveData", 10000, &resumeTask1, 0, &Task1, 1);
  xTaskCreatePinnedToCore(processAndUploadData, "UploadData", 10000, &resumeTask0, 0, &Task0, 0);
}

void loop()
{
  delay(10);
}
