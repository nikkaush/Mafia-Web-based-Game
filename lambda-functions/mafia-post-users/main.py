import json
import mysql.connector
from mysql.connector import Error
from time import gmtime, strftime

def lambda_handler(event, context):
    # "INSERT INTO Users (Display_Name, Password, Date_Created) VALUES ('Nate_Test_3', 'asdf', '0000-00-00 00:00:00')"
    # TODO implement
    # headers = {"headers": event.headers}
    connection = mysql.connector.connect(host='DATABASE_URL',
                                         database='DATABASE_NAME',
                                         user='DATABASE_USERNAME',
                                         password='DATABASE_PASSWORD')
    if "Display_Name" not in event["body"] or "Password" not in event["body"]:
        return {
                "statusCode": 400,
                "body": "Missing Display_Name or Password"
            }             
    sql_select_Query = 'select * from Users where Display_Name="{}"'.format(event["body"]["Display_Name"])
    cursor = connection.cursor()
    cursor.execute(sql_select_Query)
    records = cursor.fetchall()
          
    if len(records) > 0:
        return {
            "statusCode": 400,
            "body": "Display_Name taken already"
        }
            
    sql_select_Query = 'select * from Users where Password="{}"'.format(event["body"]["Password"])
    cursor = connection.cursor()
    cursor.execute(sql_select_Query)
    records = cursor.fetchall()
    if len(records) > 0:
        return {
            "statusCode": 400,
            "body": "Password taken already"
        }
            
    curr_time = str(strftime("%Y-%m-%d %H:%M:%S", gmtime()))
    sql_insert_Query = "INSERT INTO Users (Display_Name, Password, Date_Created) VALUES ('{}', '{}', '{}')".format(event["body"]["Display_Name"], event["body"]["Password"], curr_time)
    cursor = connection.cursor()
    cursor.execute(sql_insert_Query)
    connection.commit()
    
    sql_select_Query = 'select * from Users where Display_Name="{}"'.format(event["body"]["Display_Name"])
    cursor = connection.cursor()
    cursor.execute(sql_select_Query)
    records = cursor.fetchall()
    user = records[0]
    
    sql_insert_Query = "INSERT INTO User_Statistics (User_ID) VALUES ({})".format(user[0])
    cursor = connection.cursor()
    cursor.execute(sql_insert_Query)
    connection.commit()
    
    body_return = {"User_ID": str(user[0]), "Display_Name": event["body"]["Display_Name"], "Password": event["body"]["Password"], "Date_Created": str(user[3])}
    return {
                "statusCode": 200,
                "body": body_return
            }  
