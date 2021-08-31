### Launch app
- cd sbt-run
- sbt
- ~run

### Query by parameters "duration", "location", and "allergy" via a browser
#### Parameter "duration" possible values:
- "monday", "tuesday", "wednesday", "thursday", and "friday" for menu by day
- "week" for menu by current week
- return "n/a" if left blank
- ####localhost:9000/menu?location=innopoli&duration=week&allergy=
- localhost:9000/menu?location=kvarkki&duration=tuesday&allergy=milk


#### Parameter "location" possible values: "innopoli", "kvarkki", and "tietotekniikantalo"
- "innopoli" for Sodexo Technopolis Innopoli 1
- "kvarkki" for Sodexo Aalto Kvarkki
- "tietotekniikantalo" for Aalto Tietotekniikantalo
- return "n/a" if left blank
- localhost:9000/menu?location=tietotekniikantalo&duration=week&allergy=
- localhost:9000/menu?location=tietotekniikantalo&duration=friday&allergy=rice


#### Parameter "allergy" possible values:
- leave it blank it you don't want to filter
- "milk", "egg", etc. for one-item allergy
- "milk,egg,bread" for multiple-item allergy (no gap between ",")
