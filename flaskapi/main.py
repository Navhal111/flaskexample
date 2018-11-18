#################
#### imports ####
#################
import os
from flask import Flask
from flask_cors import CORS, cross_origin

################
#### config ####
################
app = Flask(__name__, static_url_path='/static',template_folder='/app/api/templates')
CORS(app)


####################
#### blueprints ####
####################
from api.views import api_blueprint
#
app.register_blueprint(api_blueprint, url_prefix='/api/v1')

if __name__ == '__main__':
    app.config['MAX_CONTENT_LENGTH'] = 50 * 1024 *1024
    app.run(debug="true", host="0.0.0.0",threaded=True)
