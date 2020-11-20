import json
import mysql.connector
from mysql.connector import Error

def lambda_handler(event, context):
    # TODO implement
    # try:
    # headers = {"headers": event.headers}
    connection = mysql.connector.connect(host='mafia-database-01.ckkqskhi5h3i.us-east-1.rds.amazonaws.com',
                                         database='mafia_database_001',
                                         user='admin',
                                         password='mafiaadminpassword514')
    if "Display_Name" in event["body"] and "Password" in event["body"]:
        sql_select_Query = "select * from Users where Display_Name='{}'".format(event["body"]["Display_Name"])
        cursor = connection.cursor()
        cursor.execute(sql_select_Query)
        records = cursor.fetchall()
        if len(records) < 1:
            return {
                "statusCode": 403,
                "body": None
            }
        user = records[0]
        if user[2] == event["body"]["Password"]:
            body_return = {"User_ID": str(user[0]), "Display_Name": str(user[1]), "Password": str(user[2]), "Date_Created": str(user[3])}
            return {
                "statusCode": 200,
                "body": body_return
            }
        else:
            return {
                "statusCode": 403,
                "body": None
            }
    else:
        return {
                "statusCode": 403,
                "body": None
        }