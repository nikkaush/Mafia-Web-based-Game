import json
import mysql.connector
from mysql.connector import Error

def lambda_handler(event, context):
    # TODO implement
    # try:
    print(event)
    # headers = {"headers": event.headers}
    connection = mysql.connector.connect(host='DATABASE_URL',
                                         database='DATABASE_NAME',
                                         user='DATABASE_USERNAME',
                                         password='DATABASE_PASSWORD')
                                         
    if "User_ID" in event["headers"]:
        sql_select_Query = "select * from Users where User_ID= " + event["headers"]["User_ID"]
        cursor = connection.cursor()
        cursor.execute(sql_select_Query)
        records = cursor.fetchall()
        if len(records) < 1:
            return {
                "statusCode": 200,
                "body": None
            }
        user = records[0]
        body_return = {"User_ID": str(user[0]), "Display_Name": str(user[1]), "Password": str(user[2]), "Date_Created": str(user[3])}
        
    else:
        sql_select_Query = "select * from Users"
        cursor = connection.cursor()
        cursor.execute(sql_select_Query)
        records = cursor.fetchall()
        to_json = []
        for row in records:
            to_json.append({"User_ID": str(row[0]), "Display_Name": str(row[1]), "Password": str(row[2]), "Date_Created": str(row[3])})
        body_return = {"Users": to_json}

    return {
        'statusCode': 200,
        'body': body_return
    }
