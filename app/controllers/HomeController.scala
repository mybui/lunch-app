package controllers

import models._
import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.io.Source

@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  val source: String = Source.fromFile("data/technoInno.json").getLines().mkString
  val json: JsValue = Json.parse(source)

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def menu(duration: String, location: String) = Action { implicit request: Request[AnyContent] =>
    if (location == "innopoli") {
      if (duration == "monday") {
        Ok(views.html.menu(getMealByDay(json = json, day = "monday"), "Innopoli"))
      }
      else {
        Ok(views.html.menu("nothing", "nowhere"))
      }
    } else {
      Ok(views.html.menu("nothing", "nowhere"))
    }
  }

  def getMealByDay(json: JsValue, day: String): String = {
    if (day == "monday") {
      val title: String = getMealInfo(json = json, item = "title_en", dayNumber = 1)
      val price: String = getMealInfo(json = json, item = "price", dayNumber = 1)
      val diet: String = getMealInfo(json = json, item = "dietcodes", dayNumber = 1)
      val allergy: String = getMealInfo(json = json, item = "allergy", dayNumber = 1)
      val monday: Meal = new Meal(title = title, price = price, diet = diet, allergy = allergy)
      return monday.toString()
    } else {
      val monday: Meal = new Meal(title = "n/a", price = "n/a", diet = "n/a", allergy = "n/a")
      return monday.toString()
    }
  }

  def getMealInfo(json: JsValue, item: String, dayNumber: Int): String = {
    var itemVal: String = "n/a"
    try {
      if (item == "allergy") {
        itemVal = Json.prettyPrint((json \ "mealdates" \ dayNumber \ "courses" \ dayNumber.toString() \ "additionalDietInfo" \ "allergens").get)
      } else {
        itemVal = Json.prettyPrint((json \ "mealdates" \ dayNumber \ "courses" \ dayNumber.toString() \ item).get)
      }
    } catch {
      case unknown: Throwable => println("Unknown error")
    }
    return itemVal
  }
}
