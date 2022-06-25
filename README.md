# Cow-Manager-

## INDEX
 
> PAGE NO.
1. BACKGROUND 
 
2. PROBLEM DEFINITION 
3. EXECUTIVE SUMMARY 
4. KEY FEATURES 
5. SOCIAL BENEFITS 
6. SENSOR STACK 
7. TECHNICAL DESCRIPTION 
 1. Process 
 2. Circuit diagram 
 3. Prototype 
 4. App 
8. BILL OF MATERIALS 
9. INTELLECTUAL PROPERTIES AND SPECIAL PERMITS 
10. LIMITS AND LIABILITIES 
11. FUTURE VISION
12. BIBLIOGRAPHY 

## BACKGROUND 

India has been the leading producer and consumer of dairy products worldwide since 1998,
with sustained growth in the availability of milk and milk products. Dairy activities form an 
essential part of the rural Indian economy, serving as an essential source of employment and 
income. India also has the largest bovine population in the world. However, the milk 
production per animal is significantly low compared to the other major dairy producers. 
Moreover, nearly all dairy products in India are consumed domestically, with the majority
being sold as fluid milk. On account of this, the Indian dairy industry holds tremendous 
potential for value-addition and overall development.
Along with offering profitable business opportunities, the dairy industry in India serves as a 
tool forsocio-economic development. The dairy market in India reached a value of INR 13,174
billion in 2021. IMARC (International Market Analysis Research and Consulting Group) Group 
expects the market to reach INR 30,840 billion by 2027, exhibiting at a CAGR of 14.98% during 
2022 – 2027.
 
## PROBLEM DEFINITION 

Maintaining cattle's proper health and well-being is vital for sustainable milk production in 
dairy farms. However, it is tough to do day to day monitoring of each animal on the farm, 
especially on large farms. Employees don't have enough time to observe animals and detect 
the first symptoms of diseases or other health issues. There is no device in India to track cows' 
health live 24/7. 

## EXECUTIVE SUMMARY 

The health of a cow and the quality of its milk are intrinsically linked. Furthermore, over 15 
contagiously diseases result in a reduction of milk production in cows. After observing such 
problems in the industry, we decided to make Moore. Moore is a cattle monitoring solution 
that detects anomalies in cattle behaviour. We believe that if we can quickly detect changes 
in a cow's behaviour, we can give the dairy farmer the necessary information to observe, 
diagnose or separate the infected cow and reduce losses in milk production. The Moore 
Solution is made up of 4 significant components.

1. The Moore Collar
2. A Wi-Fi Router
3. Cloud Database (Firebase)
4. Moore Mobile App

## KEY FEATURES 

1. Detection of anomalies in cow walking and resting, which are indicators of diseases 
in cows
2. Easy monitoring of cow temperature 
3. Interactive UI allows easy access and understanding of data to the farmer
4. An entirely solar-powered device with a battery backup of three days
5. Smart connection to router will enable us to save Wi-Fi bandwidth
6. The cost-efficient design will make the product affordable for farmers
 
## SOCIAL BENEFITS

The IoT setup primarily aims to help dairy farmers maintain their cattle herds. This device 
can help continuously monitor the cattle without any human involvement. As the collar is 
continuously tracking the moment and temperature of the cattle and using the AI-enabled 
algorithm, we can track the activity of the cattle. If there is some deviation in the normal 
activity cycle of the cattle, the algorithm responds accordingly, and necessary action can be 
taken. This will ultimately help the dairy farmers to take necessary action at an earlier stage. 
This will ultimately help in – • Maintaining Cattle Health
• Increase the Quality and Quantity of Milk Produced -
The healthier the cattle, the more the milk will produce and the more energy to do 
tasks.
• Greater Financial Freedom to Individual Dairy Farmers -
This increased milk yield will bring in more revenue for the farmers and give them a 
greater degree of financial freedom. This allows a level of class mobility to farmers 
through the rise of their profits.
 
## SENSOR STACK 

1. MPU 6050
  1. IMU sensor module is complete 6-axis Motion Tracking
  2. It combines 3-axis Gyroscope, 3-axis Accelerometer and Digital Motion Processor
  3. Accelerometer range(g): ± 4 • Gyroscope range(Â°/s): ± 500
4 | P a g e
2. DS18B20 
• Temperature sensor
• Usable temperature range: -55 to 125°C (-67°F to 
+257°F)
• ±0.5°C Accuracy from -10°C to +85°C
 
## TECHNICAL DESCRIPTION 

### PROCESS

1. Acquiring the sensor data –
We have used the Esp32 development board in our prototype, which has an inbuilt 
Wi-Fi functionality. Esp32 can store and read the sensors' data to ascertain cattle's 
orientation and actions, which supports judging the cattle's health. In developing our 
prototype, we have used the MPU6050 Accelerometer-Gyroscope module DS18B20 
probe-type Temperature Sensor.
• MPU6050 is low power and low-cost 6-axis gyroscope and accelerometer 
used to define cattle's orientation and track its movement using 3-axis 
acceleration. Esp32 communicates with the sensor using the I2C 
communication protocol and then stores these acceleration values, which 
will define the cattle's orientation.
• DS18B20 is a probe-type low power temperature sensor that measures 
the temperature of a body in contact with the probe and sends the 
temperature readings in the form of an analog signal, which are read by 
the Esp32 microcontroller. This data this then sent to the server.
ESP 32 has two cores. Core 1 continuously monitors all the sensor data and stores 
them in a queue (queue store_data), implemented using a circular array of enough 
size. This process is uninterrupted, which ensures no loss of data. 
2. Pre-processing the data –
Core 0 occasionally wakes up to pre-process the available data in the store_data 
queue. It processes 16 data units (each consisting of accelerometer and gyroscopic 
data, temperature value) to obtain the average deviation, RMS value, Peak to peak 
value, vectorial dynamic body acceleration (VeDBA) and crest factor. The processed 
data is then stored in a second queue (queue processed_data) implemented using 
another circular array of larger size. This process is repeated at regular intervals.
3. Transmitting data to the server –
When there are enough data points to send (every 15 minutes), Core 0 will power up 
Wi-Fi on esp32, connect to a nearby Wi-Fi network, and establish a connection with 
the FireBase real-time database. The core then will start uploading the data to the 
database.
Every neck collar will have its designated identification code, and it will be linked to a 
particular cow ID. This information is also available on the database. The ESP first 
reads this cow ID and appends data correspondingly into the Cow's Database.
4. Using an ML model to predict cow's health
After the data is uploaded onto the cloud database, the data for each cow is passed 
through a Machine Learning model. The machine learning model was trained using 
similar data using a random decision forests algorithm to classify the different 16-
second samples into different behaviour categories of cattle: low activity behaviours 
(lying, standing, ruminating) and high activity behaviours (feeding, walking). This 
data is then summed to find the total time for different activities for every 15 
minutes time frame and stored; for every hour, an activity index is created, which 
gives the ratio of change in hourly activity levels compared to activity levels from the 
same time period from the previous two days. This is useful for identifying abnormal 
behaviours or checking for extreme deviation from the normal routine.
Along with the activity metrics, the cow's temperature is also compared to its values 
for the previous two days to identify any abrupt change in normal temperatures.
5. Making the processed data available to farmers through a mobile app
The user is given provisions to login to the app using the phone number and OTP 
using FireBase authentication. The user will have to initially set up the barns and cow 
details (cow name, belt ID, breed, History of diseases and vaccinations) in each barn. 
Then the user will have to link the belt to the cow using the belt ID. Then the user 
will get the information about cow's health which includes pregnancy cycle, 
temperature and unusual behaviour. The cows with such problems will be 
highlighted, and the barns containing unhealthy cows will also be highlighted. The 
user will have the option to add new barns and cows. The user will also have a 
dashboard to see barn status and total linked cows.
 

### CIRCUIT DIAGRAM

### PROTOTYPE 

The prototype is cased around a 6.5 x 7 x 2 cm casing of a 2mm 
thick plastic case. It has two parts the body and the lid. All the 
circuitry, including the microcontroller and sensors, are placed 
inside the body with a cut for the temperature sensor. Solar 
panels will be placed over the lid.
There are hooks provided at the bottom surface to hook the 
device to the cow's collar.
### APP 

#### TECH STACK

1. Android Studio (JAVA, XML) 
2. Material UI
3. FireBase
• Authentication
• Realtime database
4. Views 
• Recycler view
• Search bar
• Alert dialogue box
• Pie chart for temperature data
• Fragment
• Bottom navigation bar
The app is implemented in Android Studio using JAVA and XML. Material 
UI is used for attractive and modern UI. DataBinding is used to bind UI 
components in the layouts to data sources in the app. Authentication of 
the user is done by Mobile number / OTP using firebase Authentication, 
auto-fill functionality of OTP is implemented, and a timer is displayed for 
the user to wait accordingly for the subsequent OTP request. Dashboard 
and Profile Fragment are navigated through the bottom navigation bar. 
Barn's and cow's data is fetched from the firebase real-time database and 
displayed in RecyclerView, including the status of Barn/Cow depicted by 
the colour of the RecyclerView item. Search Functionality is added using 
the textChangedListner function of TextWatcher class.
The temperature of the cow is displayed using a piechart UI object.
Realtime data is fetched for each cow, and if an unusual behaviour flag 
turns true, then an alert is sent to the user indicating the possibility of 
disease.

## BILL OF MATERIALS 

#### COMPONENT COST (RS)

<table> 
  <tr>
  <td> Component </td>
  <td> Cost </td>
  </tr>
  <tr>
    <td> ESP32-D0WDQ6 Dual-core </td> <td>  170 </td>
  </tr>
  <tr> 
    <td> MPU6050 </td>  <td> 110 </td>
  </tr>
  <tr>
    <td>
      DS18B20
    </td> 
    <td>
      60
    </td>
  </tr>
  <tr> <td> CN3065 18650 Li-ion Mini Solar Charger Module </td> <td>150 </td> </tr>
  <tr> <td> Solar panels (6v, 80 mAH) </td> <td> 30 </td> </tr>
<tr> <td> Battery (3.7v, 1200mAH) </td> <td> 140  </td> </tr>
<tr> <td> Buck booster </td> <td> 40  </td> </tr>
<tr> <td> Casing and Collar </td> <td> 150  </td  </td> </tr>> </tr>
<tr> <td> Total cost of one Moore Collar  </td> <td> Rs 850/-  </td> </tr>
<tr> <td> Cost of router </td> <td> Rs 800 /- (Depended on Barn Size)  </td> </tr>
<tr> <td> FireBase maintenance cost </td> <td> Rs 20/- per cow per year  </td> </tr>
<tr> <td> Overall maintenance cost </td> <td> Rs 16/- per cow per year  </td> </tr>
</table>


  ## INTELLECTUAL PROPERTIES AND SPECIAL PERMITS
  
None. Only open-source and freely licensed material is used for specific parts, and the rest is 
indigenous.

 ## LIMITS AND LIABILITIES
 
• We are using google cloud services and not of our own.
• This method only detects anomalies indicative of diseases but does not diagnose 
them.
• Farmers need to be educated to use android apps
• The farm should have internet facility

 ## FUTURE VISION
 
• The Moore Collar can be expanded for other livestock animals.
• Similar Moore Collars can be used to track wildlife to better study and track 
them in their natural habitat.
• Weather prediction can be integrated into the routers and be used to help the 
farmer inform his future decisions

# BIBLIOGRAPHY 

https://www.ece.cmu.edu/news-and-events/story/2021/09/monitoring-cow-movement.html
https://www.andrew.cmu.edu/user/shijiapa/documentations/data18-final20.pdf
https://www.ijert.org/iot-based-cattle-health-monitoring￾system#:~:text=The%20main%20aspect%20of%20health,like%20temperature%2C%20hear
t%20rate%20etc.
https://www.ncbi.nlm.nih.gov/pmc/articles/PMC7302546/
