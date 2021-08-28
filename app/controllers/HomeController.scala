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
        /*
        Ok(views.html.menu(getMealByDay(json = json, day = "monday")(0),
          "Innopoli",
          getMealByDay(json = json, day = "monday")(1), 
          getMealByDay(json = json, day = "monday")(2)))
         */
        Ok(views.html.menu(getMealByDay(json = json, day = "monday"), "Innopoli"))
      }
      else {
        Ok(views.html.menu("n/a", "n/a"))
      }
    } else {
      Ok(views.html.menu("n/a", "n/a"))
    }
  }

  def getMealByDay(json: JsValue, day: String): String = {
    if (day == "monday") {
      /*var output = Array.ofDim[String](3, 2)*/
      var output: String = """
                             |
                             |
                             |""".stripMargin + "   MONDAY" + """
                                                                |
                                                                |""".stripMargin
      var index: Int = 1
       breakable
       {
         while (index <= 10) {
           val title: String = getMealInfo(json = json, item = "title_en", dayNumber = 0, courseNumber = index)
           val price: String = getMealInfo(json = json, item = "price", dayNumber = 0, courseNumber = index)
           val diet: String = getMealInfo(json = json, item = "dietcodes", dayNumber = 0, courseNumber = index)
           val allergy: String = getMealInfo(json = json, item = "allergy", dayNumber = 0, courseNumber = index)
           val course: Meal = new Meal(title = title, price = price, diet = diet, allergy = allergy)
           /*output(2)(0) = course.toString()
           output(2)(1) = diet
           println(output(2)(1))*/
           if (title == "n/a") break
           output += "     - " + course +
             """
               |
               |
               |""".stripMargin
           /*output :+ course.toString()*/
           index += 1
         }
       }
      /*return Array(monday.toString(), diet.replace("\"", ""), allergy.replace("\"", ""))*/
      return output
    } else {
      /*val monday: Meal = new Meal(title = "n/(a)", price = "n/a", diet = "n/a", allergy = "n/a")
      return Array(monday.toString(), "n/a", "n/a")*/
      return "n/a"
    }
  }

  def getMealInfo(json: JsValue, item: String, dayNumber: Int, courseNumber: Int): String = {
    var itemVal: String = "n/a"
    try {
      if (item == "allergy") {
        /*println(dayNumber + stepper)*/
        /*println(Json.prettyPrint((json \ "mealdates" \ dayNumber \ "courses" \ courseNumber.toString()).get))*/
        itemVal = Json.prettyPrint((json \ "mealdates" \ dayNumber \ "courses" \ courseNumber.toString() \ "additionalDietInfo" \ "allergens").get)
        /*println(itemVal)*/
      } else {
        itemVal = Json.prettyPrint((json \ "mealdates" \ dayNumber \ "courses" \ courseNumber.toString() \ item).get)
        /*println(itemVal)*/
      }
    } catch {
      case unknown: Throwable => println("Unknown error")
    }
    return itemVal
  }
}
