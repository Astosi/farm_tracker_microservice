# FarmTracker-services-java
- [See Project Page](https://github.com/users/Sharaido/projects/4)

## Database
- [See Database ER Diagram](https://github.com/Sharaido/FarmTracker-files/blob/main/database/db.png)
- [See Database Script (MSSQL)](https://github.com/Sharaido/FarmTracker-files/blob/main/database/farmTracker.sql)

## Setup
- [Setup](#Setup)

## Functions

- [Get all plant types](#Get-all-plant-types)
- [Get plant type by id](#Get-plant-type-by-id)
- [Get plant type by name](#Get-plant-type-by-name)
- [Get plant type's proper temperature](#Get-proper-temperature)
- [Get plant type's days to harvest](#Get-days-to-harvest)
- [Get plant type's proper humidity](#Get-proper-humidity)
- [Get plant type's proper irrigation](#Get-proper-irrigation)
- [Get plant type's proper precipitation](#Get-proper-precipitation)
- [Get plant type's proper fertilization](#Get-proper-fertilization)
- [Get plant type's proper temperature differece](#Get-proper-temperature-differece)

- [Create plant](#Create-plant)
- [Get address](#Get-address)
- [Get next harvest date](#Get-next-harvest-date)
- [Set watered days](#Set-watered-days)
- [Get suggeested plants](#Get-suggested-plants)
- [Get reminders](#Get-reminders)
- [Get warnings](#Get-warnings)
- [Get sunrise-sunset time](#Get-sunrise-sunset-time)


### Setup

#### Sql Management Studio 
- Create a new login in database server
- To do this go security/logins
- Right click to logins and choose new login
- Determine a login name and password(do not forget to switch it sql server authentication from windows authentication)
- Hit OK button
- Go to demo/src/main/resources/application.properties.
- Next, right-click to database server(on top in sql management studio computer_name\sqlexpress) and hit properties
- Go to security
- and ensure that sql server and windows authentification mode is checked
- Go to permissions in same window
- You must be seeing your user in that list
- Choose your user and hit effective
- Ensure that your user has permission of connect sql and view any database

#### Idea 
- Go to demo/src/main/resources/application.properties
- Connect the database

spring.datasource.url=jdbc:sqlserver://"name-of-your-pc"\\SQLEXPRESS;databaseName="database-name"
spring.datasource.username="user-you-created"
spring.datasource.password="password"

#### Notes
- Ensure that sqlserver browser is running(you can check in local services on Windows)


### Plant type information

#### Get all plant types
- Get all plant types in database

##### Request 
	GET http://localhost:8080/api/v1/plant_type/all
##### Response
	[
    {
        "plant_name": "example_plant
        "proper_fertilization": "...
        "proper_precip": 4.0,
        "proper_tempchg": 10.0,
        "proper_humidity": 60.0,
        "min_temp": 4.0,
        "max_temp": 40.0,
        "min_irrigation": 2,
        "max_irrigation": 4,
        "days_harvest": 90,
        "ptuid": "plant-type-id
    }
    ...
]
  

#### Get plant type by id
- Get plant by id

##### Request 
	GET http://localhost:8080/api/v1/plant_type/find_by_id/{id}
##### Response
	 {
        "plant_name": "example_plant
        "proper_fertilization": "...
        "proper_precip": 4.0,
        "proper_tempchg": 10.0,
        "proper_humidity": 60.0,
        "min_temp": 4.0,
        "max_temp": 40.0,
        "min_irrigation": 2,
        "max_irrigation": 4,
        "days_harvest": 90,
        "ptuid": "plant-type-id
    }

#### Get plant type by name
- Get plant type by name

##### Request 
	GET http://localhost:8080/api/v1/plant_type/{name}

##### Response
 {
        "plant_name": "example_plant
        "proper_fertilization": "...
        "proper_precip": 4.0,
        "proper_tempchg": 10.0,
        "proper_humidity": 60.0,
        "min_temp": 4.0,
        "max_temp": 40.0,
        "min_irrigation": 2,
        "max_irrigation": 4,
        "days_harvest": 90,
        "ptuid": "plant-type-id
    }


#### Get proper temperature
- Get proper temperature of the plant


##### Request 
	GET http://localhost:8080/api/v1/plant_type/{plantName}/proper_temp
##### Response
	
    "max ": 40.0,
    "min ": 4.0


#### Get days to harvest
- Get average days to harvest for the plant

##### Request 
	GET http://localhost:8080/api/v1/plant_type/{plantName}/days_to_harvest

##### Response

		90
	 
#### Get proper humidity
- Get proper humidity for the plant.

##### Request 
	GET http://localhost:8080/api/v1/plant_type/{plantName}/prop_hum
##### Response
	
    60.0
	 


#### Get proper irrigation
- Get proper irrigation rouine for the plant.

##### Request 
	GET http://localhost:8080/api/v1/plant_type/{plantName}/prop_irr
  
##### Response
{
    "max ": 4,
    "min ": 2
}

#### Get proper precipitation
- Get average proper precopitaion for the plant(m3 per m2)

##### Request 
	GET http://localhost:8080/api/v1/plant_type/{plantName}/prop_percip 
##### Response

    4.0


#### Get proper fertilization
- Get a string message about proper fertilization.


##### Request 
	GET http://localhost:8080/api/v1/plant_type/{plantName}/prop_ferz 
##### Response
 message..........


#### Get proper temperature differece
- Get proper temperature difference for th plant


##### Request 
	GET http://localhost:8080/api/v1/plant_type/{plantName}/prop_temp_diff 
##### Response
  10.0
 
 
### Suggestions, warnings, reminders and location data

#### Create plant

##### Request Body
{
 "plantName" : "pepper",
 "plantingDate" : "2020-12-23",
 "longitude" : "27.141308",
 "latitude" : "38.437076"
}

##### Request 
	POST http://localhost:8080/api/v1
 

#### Get address
- Get address details

##### Request 
	Get http://localhost:8080/api/v1/get_address
  
##### Response  
 {
    "country": "Turkey",
    "country_code": "tr",
    "street": "Atatürk Caddesi",
    "suburb": "Kültür Mahallesi",
    "state": "Aegean Region"
}


#### Get next harvest date
- Get next harvest date. It will use the plant name to find data about created plant. If it does not exist you won't able to use it

##### Request 
	GET http://localhost:8080/api/v1/next_harvest

##### Response
	2021-03-23 


#### Set watered days
- You have to send a list of dates of irrigation data, otherwise reminders won't work properly

##### Request Body

    ["2020-12-25" , "2020-12-27"]
	
  
##### Request 
	PUT http://localhost:8080/api/v1/set_IfWatered


#### Get suggested plants
- Get suggested plants.

##### Request 
	GET http://localhost:8080/api/v1/sug_plants

##### Response
	[
    {
        "plant_name": "example_plant
        "proper_fertilization": "...
        "proper_precip": 4.0,
        "proper_tempchg": 10.0,
        "proper_humidity": 60.0,
        "min_temp": 4.0,
        "max_temp": 40.0,
        "min_irrigation": 2,
        "max_irrigation": 4,
        "days_harvest": 90,
        "ptuid": "plant-type-id
    }
    ...
]


#### Get reminders
- Get reminders about irrigation.(It uses weather reports to support data)

##### Request 
	GET http://localhost:8080/api/v1/reminders

##### Response
{
    "pepper": {
        "reminder": "2 days without watering for pepper",
        "suggestion": "no suggestions"
    }
}


#### Get warnings
- Get warning messages about the plant

##### Request 
	GET http://localhost:8080/api/v1/warnings
  
##### Response
	{
    "pepper": {
        "temp_change_warn": "Expecting extreme temp change on dates [2020-12-26]. It can be dangerous for pepper",
        "snow_warn": "no warning",
        "over_heat_warn": "no warning",
        "low_temp_warn": "Expecting low temp on [2020-12-26, 2020-12-28]. It can be dangerous for pepper",
        "irrigation_warn": "no warning"
    }
}


#### Get sunrise sunset time
- Get sunrise and sunset times

##### Request 
	GET http://localhost:8080/api/v1/sunrise_set
##### Response
{
    "sunrise": "08:15 AM",
    "sunset": "05:37 PM"
}
