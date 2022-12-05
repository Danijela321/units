"# units" 

Request
POST:
http://localhost:8080/goteborg.se/api/
{
"id": "unit1",
"name": "Valhallbadet",
"address" : "Badgatan 2",
"category": ["simhall","simskola"]
}


{
"id": "unit2",
"name": "Rosen fritidsgård",
"address" : "Fritidsgårdsgatan 1",
"category": ["fritidsgård"]
}

{
"id": "unit3",
"name": "xxx",
"address": "xxx 2",
"category": [
"simhall",
"fritidsgård"
]
}

GET all
http://localhost:8080/goteborg.se/api/

Get by name
http://localhost:8080/goteborg.se/api/name/Valhallbadet
http://localhost:8080/goteborg.se/api/name/Rosen fritidsgård

Get by id
http://localhost:8080/goteborg.se/api/id/unit2

Get by category
http://localhost:8080/goteborg.se/api/category/fritidsgård
