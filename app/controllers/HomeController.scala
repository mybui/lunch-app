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
  val source: String = Source.fromFile("data/technoInno.json").getLines().mkString
  val json: JsValue = Json.parse(source)

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def menu(duration: String, location: String/*, diet: String, allergy: String*/) = Action { implicit request: Request[AnyContent] =>
    if (location == "innopoli") {
      if (duration == "monday") {
        Ok(views.html.menu(getMealByDay(json = json, day = "monday"), "Innopoli"))
      }
      else if (duration == "tuesday") {
        Ok(views.html.menu(getMealByDay(json = json, day = "tuesday"), "Innopoli"))
      }
      else if (duration == "wednesday") {
        Ok(views.html.menu(getMealByDay(json = json, day = "wednesday"), "Innopoli"))
      }
      else if (duration == "thursday") {
        Ok(views.html.menu(getMealByDay(json = json, day = "thursday"), "Innopoli"))
      }
      else if (duration == "friday") {
        Ok(views.html.menu(getMealByDay(json = json, day = "friday"), "Innopoli"))
      }
      else {
        Ok(views.html.menu("n/a", "Innopoli"))
      }
    } else {
      Ok(views.html.menu("n/a", "n/a"))
    }
  }

  def getMealByDay(json: JsValue, day: String): String = {
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
    println(dayNumber)
    var output: String = """|
                            |
                            |""".stripMargin + "                 " + day.toUpperCase() + """
                                                                                            |
                                                                                            |""".stripMargin
    breakable
    {
      var index: Int = 1
      while (index <= 10) {
        val title: String = getMealInfo(json = json, item = "title_en", dayNumber = dayNumber, courseNumber = index)
        val price: String = getMealInfo(json = json, item = "price", dayNumber = dayNumber, courseNumber = index)
        val diet: String = getMealInfo(json = json, item = "dietcodes", dayNumber = dayNumber, courseNumber = index)
        val allergy: String = getMealInfo(json = json, item = "allergy", dayNumber = dayNumber, courseNumber = index)
        val course: Meal = new Meal(title = title, price = price, diet = diet, allergy = allergy)
        if (title == "n/a") break
        output += "                    - " + course +
          """
             |
             |
             |""".stripMargin
        index += 1
      }
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
      case unknown: Throwable => println("Unknown error")
    }
    return itemVal
  }
}
