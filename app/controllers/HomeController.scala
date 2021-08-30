package controllers

import models._
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.io.Source
import scala.util.control.Breaks._

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  val technoInno: String = Source.fromFile("data/technoInno.json").getLines().mkString
  val technoInnoJson: JsValue = Json.parse(technoInno)
  val kvarkki: String = Source.fromFile("data/kvarkki.json").getLines().mkString
  val kvarkkiJson: JsValue = Json.parse(kvarkki)
  val tietotekniikantalo: String = Source.fromFile("data/tietotekniikantalo.json").getLines().mkString
  val tietotekniikantaloJson: JsValue = Json.parse(tietotekniikantalo)

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def menu(duration: String, location: String, allergy: String) = Action { implicit request: Request[AnyContent] =>
    if (location.toLowerCase() == "innopoli") {
      if (duration.toLowerCase() == "monday") {
        Ok(views.html.menu(getMealByDay(json = technoInnoJson, day = "monday", allergyInfo = allergy), "Technopolis Innopoli", allergy))
      } else if (duration.toLowerCase() == "tuesday") {
        Ok(views.html.menu(getMealByDay(json = technoInnoJson, day = "tuesday", allergyInfo = allergy), "Technopolis Innopoli", allergy))
      } else if (duration.toLowerCase() == "wednesday") {
        Ok(views.html.menu(getMealByDay(json = technoInnoJson, day = "wednesday", allergyInfo = allergy), "Technopolis Innopoli", allergy))
      } else if (duration.toLowerCase() == "thursday") {
        Ok(views.html.menu(getMealByDay(json = technoInnoJson, day = "thursday", allergyInfo = allergy), "Technopolis Innopoli", allergy))
      } else if (duration.toLowerCase() == "friday") {
        Ok(views.html.menu(getMealByDay(json = technoInnoJson, day = "friday", allergyInfo = allergy), "Technopolis Innopoli", allergy))
      } else if (duration.toLowerCase() == "week") {
        Ok(views.html.menu(getMealByDay(json = technoInnoJson, day = "week", allergyInfo = allergy), "Technopolis Innopoli", allergy))
      } else {
        Ok(views.html.menu("n/a", "Technopolis Innopoli", "n/a"))
      }
    } else if (location.toLowerCase() == "kvarkki") {
      if (duration.toLowerCase() == "monday") {
        Ok(views.html.menu(getMealByDay(json = kvarkkiJson, day = "monday", allergyInfo = allergy), "Aalto Kvarkki", allergy))
      } else if (duration.toLowerCase() == "tuesday") {
        Ok(views.html.menu(getMealByDay(json = kvarkkiJson, day = "tuesday", allergyInfo = allergy), "Aalto Kvarkki", allergy))
      } else if (duration.toLowerCase() == "wednesday") {
        Ok(views.html.menu(getMealByDay(json = kvarkkiJson, day = "wednesday", allergyInfo = allergy), "Aalto Kvarkki", allergy))
      } else if (duration.toLowerCase() == "thursday") {
        Ok(views.html.menu(getMealByDay(json = kvarkkiJson, day = "thursday", allergyInfo = allergy), "Aalto Kvarkki", allergy))
      } else if (duration.toLowerCase() == "friday") {
        Ok(views.html.menu(getMealByDay(json = kvarkkiJson, day = "friday", allergyInfo = allergy), "Aalto Kvarkki", allergy))
      } else if (duration.toLowerCase() == "week") {
        Ok(views.html.menu(getMealByDay(json = kvarkkiJson, day = "week", allergyInfo = allergy), "Aalto Kvarkki", allergy))
      } else {
        Ok(views.html.menu("n/a", "Aalto Kvarkki", "n/a"))
      }
    } else if (location.toLowerCase() == "tietotekniikantalo") {
      if (duration.toLowerCase() == "monday") {
        Ok(views.html.menu(getMealByDay(json = tietotekniikantaloJson, day = "monday", allergyInfo = allergy), "Aalto Tietotekniikantalo", allergy))
      } else if (duration.toLowerCase() == "tuesday") {
        Ok(views.html.menu(getMealByDay(json = tietotekniikantaloJson, day = "tuesday", allergyInfo = allergy), "Aalto Tietotekniikantalo", allergy))
      } else if (duration.toLowerCase() == "wednesday") {
        Ok(views.html.menu(getMealByDay(json = tietotekniikantaloJson, day = "wednesday", allergyInfo = allergy), "Aalto Tietotekniikantalo", allergy))
      } else if (duration.toLowerCase() == "thursday") {
        Ok(views.html.menu(getMealByDay(json = tietotekniikantaloJson, day = "thursday", allergyInfo = allergy), "Aalto Tietotekniikantalo", allergy))
      } else if (duration.toLowerCase() == "friday") {
        Ok(views.html.menu(getMealByDay(json = tietotekniikantaloJson, day = "friday", allergyInfo = allergy), "Aalto Tietotekniikantalo", allergy))
      } else if (duration.toLowerCase() == "week") {
        Ok(views.html.menu(getMealByDay(json = tietotekniikantaloJson, day = "week", allergyInfo = allergy), "Aalto Tietotekniikantalo", allergy))
      } else {
        Ok(views.html.menu("n/a", "Aalto Tietotekniikantalo", "n/a"))
      }
    }
    else {
      Ok(views.html.menu("n/a", "n/a", "n/a"))
    }
  }

  def getMealByDay(json: JsValue, day: String, allergyInfo: String): String = {
    var dayNumber: Int = 0
    if (day == "tuesday") {
      dayNumber = 1
    }
    if (day == "wednesday") {
      dayNumber = 2
    }
    if (day == "thursday") {
      dayNumber = 3
    }
    if (day == "friday") {
      dayNumber = 4
    }
    var output: String = """|
                            |
                            |""".stripMargin + "                 " + day.toUpperCase() + """
                                                                                            |
                                                                                            |""".stripMargin
    if (day != "week") {
      breakable {
        var index: Int = 1
        while (index <= 8) {
          val title: String = getMealInfo(json = json, item = "title_en", dayNumber = dayNumber, courseNumber = index)
          val price: String = getMealInfo(json = json, item = "price", dayNumber = dayNumber, courseNumber = index)
          val diet: String = getMealInfo(json = json, item = "dietcodes", dayNumber = dayNumber, courseNumber = index)
          val allergy: String = getMealInfo(json = json, item = "allergy", dayNumber = dayNumber, courseNumber = index)
          val course: Meal = new Meal(title = title, price = price, diet = diet, allergy = allergy)

          if (title == "n/a") {
            break()
          }

          if (allergyInfo.toString().toLowerCase() == "0") {
            output += "                    - " + course +
              """
                 |
                 |
                 |""".stripMargin
            index += 1
          } else {
            if (allergyInfo.contains(",")) {
              var passed: Array[Boolean] = Array()
              val allergyList = allergyInfo.split(",")
              // make a loop to check for each item in allergyList
              for (item <- allergyList) {
                if (allergy.toLowerCase().split(",").forall(element => !element.contains(item.toLowerCase())) &&
                  title.toLowerCase().split(" ").forall(element => !element.contains(item.toLowerCase()))) {
                  passed :+= true
                } else {
                  passed :+= false
                }
              }
              if (!passed.contains(false)) {
                output += "                    - " + course +
                  """
                    |
                    |
                    |""".stripMargin
              }
              passed.foreach(println)
              index += 1
            } else {
              if (!allergy.toLowerCase().contains(allergyInfo.toLowerCase()) && !title.toLowerCase().contains(allergyInfo.toLowerCase())) {
                output += "                    - " + course +
                  """
                    |
                    |
                    |""".stripMargin
                index += 1
              } else {
                index += 1
              }
            }
          }
        }
      }
    } else {
      output = """|
                  |
                  |""".stripMargin + "               " + day.toUpperCase() + """
                                                                                |
                                                                                |""".stripMargin + "               ____________________________________________________________________________________________"
      output += getMealByDay(json = json, day = "monday", allergyInfo = allergyInfo)
      output += "               ____________________________________________________________________________________________"
      output += getMealByDay(json = json, day = "tuesday", allergyInfo = allergyInfo)
      output += "               ____________________________________________________________________________________________"
      output += getMealByDay(json = json, day = "wednesday", allergyInfo = allergyInfo)
      output += "               ____________________________________________________________________________________________"
      output += getMealByDay(json = json, day = "thursday", allergyInfo = allergyInfo)
      output += "               ____________________________________________________________________________________________"
      output += getMealByDay(json = json, day = "friday", allergyInfo = allergyInfo)
    }

    if (dayNumber != None) {
      return output
    }
    return "n/a"
  }

  def getMealInfo(json: JsValue, item: String, dayNumber: Int, courseNumber: Int): String = {
    var itemVal: String = "n/a"
    try {
      if (item == "allergy") {
        itemVal = Json.prettyPrint((json \ "mealdates" \ dayNumber \ "courses" \ courseNumber.toString() \ "additionalDietInfo" \ "allergens").get)
      } else {
        itemVal = Json.prettyPrint((json \ "mealdates" \ dayNumber \ "courses" \ courseNumber.toString() \ item).get)
      }
    } catch {
      case unknown: Throwable => println("Element unfound in json file")
    }
    return itemVal
  }
}
