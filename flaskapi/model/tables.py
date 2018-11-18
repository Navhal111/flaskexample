import peewee
import MySQLdb
from peewee import SQL
# from mysql.connector.constants import SQL
import datetime
import json

# from playhouse.shortcuts import RetryOperationalError


database_name ="flask_test"
host_name ="localhost"
user_name ="root"
password  =""

class DatabaseConfig(object):
    db = peewee.MySQLDatabase(database_name, host=host_name, user=user_name,passwd=password)


#System Main tables:-
#Module A
class Employee_data(peewee.Model):
    emp_id     	    = peewee.PrimaryKeyField()
    name  	        = peewee.CharField()
    email	      	= peewee.CharField()
    phone_no      	= peewee.IntegerField()
    modify_date 	= peewee.DateTimeField()

    def save(self, *args, **kwargs):
        self.modify_date = datetime.datetime.strptime(datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S'), '%Y-%m-%d %H:%M:%S')
        return super(Employee_data, self).save(*args, **kwargs)

    class Meta:
        database = DatabaseConfig.db
#Module A
DatabaseConfig.db.close()
