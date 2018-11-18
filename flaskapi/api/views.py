from flask import flash, redirect, render_template, request, \
    url_for, Blueprint, jsonify, abort, make_response,current_app, \
    Response, _app_ctx_stack, g
import flask
from flask_cors import cross_origin
from functools import wraps
import json
from peewee import *
import peewee
from model.tables import *

liveip="http://34.215.141.54"
apiport=":5000"

api_blueprint = Blueprint(
    'api', __name__,
)
def http_header(f):
    """This decorator passes Content-Type: application/json"""
    @wraps(f)
    @add_response_headers({'Content-Type': 'application/json'})
    def decorated_function(*args, **kwargs):
        return f(*args, **kwargs)
    return decorated_function


def add_response_headers(headers={}):
    """This decorator adds the headers passed in to the response"""
    def decorator(f):
        @wraps(f)
        def decorated_function(*args, **kwargs):
            resp = make_response(f(*args, **kwargs))
            h = resp.headers
            for header, value in headers.items():
                h[header] = value
            return resp
        return decorated_function
    return decorator
def raise_error(status_code, message):
    response = jsonify({
        'status': status_code,
        'message': message
    })
    response.status_code = status_code
    return response


@api_blueprint.route('/ping', methods=['GET'])
@http_header
def ping():
    try:

        return json.dumps("Up and running")
    except Exception as e:
        error="Error in add_supplier(),views.api: "+str(e)
        return raise_error(500, error)

#Add Emplopyee Bank Details
#Table:- Bank
@api_blueprint.route('/add_employee', methods=['POST'])
@http_header
def add_employee():
    try:
        incoming = json.loads(request.data)
        employee_data = Employee_data(name=incoming["name"],email=incoming["email"],phone_no=int(incoming["contact"]))
        employee_data.save()
        data = {}
        data["msg"]="Data Addeds"
        return json.dumps(data)
    except Exception as e:
        error="Error in add_employee(),views.api: "+str(e)
        return raise_error(500, error)


@api_blueprint.route('/list_employee', methods=['POST'])
@http_header
def list_employee():
    try:
        list_data = []
        employee_data = Employee_data.select(Employee_data.emp_id,Employee_data.name,Employee_data.email,Employee_data.phone_no).dicts()
        # other like join and where condition apply check pewee sytex from site
        if employee_data.exists():
            for d in employee_data:
                list_data.append(d)
        return json.dumps(list_data)
    except Exception as e:
        error="Error in list_employee(),views.api: "+str(e)
        return raise_error(500, error)

@api_blueprint.route('/list_employee_byid/empid=<empid>', methods=['POST'])
@http_header
def list_employee_byid(empid):
    try:
        list_data = []
        employee_data = Employee_data.select(Employee_data.emp_id,Employee_data.name,Employee_data.email,Employee_data.phone_no).where(Employee_data.emp_id==empid).dicts()
        # other like join and where condition apply check pewee sytex from site
        if employee_data.exists():
            for d in employee_data:
                list_data.append(d)
        return json.dumps(list_data)
    except Exception as e:
        error="Error in list_employee_byid(),views.api: "+str(e)
        return raise_error(500, error)

@api_blueprint.route('/update_employee/empid=<empid>', methods=['POST'])
@http_header
def update_employee(empid):
    try:
        incoming = json.loads(request.data)
        employee_data = Employee_data.update(name=incoming["name"],email=incoming["email"],phone_no=int(incoming["contact"])).where(Employee_data.emp_id == empid)
        employee_data.execute()
        data = {}
        data["msg"]="Data Updated"
        data["data"]=incoming
        return json.dumps(data)
    except Exception as e:
        error="Error in add_employee(),views.api: "+str(e)
        return raise_error(500, error)

@api_blueprint.route('/delete_employee/empid=<empid>', methods=['POST'])
@http_header
def delete_employee(empid):
    try:
        employee_data = Employee_data.delete().where(Employee_data.emp_id==empid)
        employee_data.execute()
        data = {}
        data["msg"]="Data Deleted"
        list_data = []
        employee_data = Employee_data.select(Employee_data.emp_id,Employee_data.name,Employee_data.email,Employee_data.phone_no).dicts()
        # other like join and where condition apply check pewee sytex from site
        if employee_data.exists():
            for d in employee_data:
                list_data.append(d)
        data["data"]=list_data
        return json.dumps(list_data)
    except Exception as e:
        error="Error in delete_employee(),views.api: "+str(e)
        return raise_error(500, error)
