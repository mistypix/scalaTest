import scala.io._
import scala.xml._
import scala.actors._
import Actor._

class MyClass(val myColor: String, var myNum: Int){
	println("hello world 1");
	//def this(val Color: String)={this(Color,0)}
	def print()={println(myColor,myNum)}
	
	def total(list: List[Int])={
	  var sum= 0;
	  for(i <- list){
	    sum+=i;
	  }
	   sum
	   
	}
	
	def totalSelectValues(list: List[Int], selector: Int => Boolean)={
	  var sum=0;
	  list.foreach{
	    e => if(selector(e))
	      sum+=e;
	  }
	  sum
	}
}


trait Friend{
  val name: String;
  def listen()={println("I am "+ name + ", your friend.")};
}



class Human(val name:String) extends Friend
class Animal(val name:String)
class Dog(override val name:String) extends Animal(name) with Friend
class Cat(override val name:String) extends Animal(name)




object MyObject extends App{
	val myClass= new MyClass("blue",0);
	var str: String = "Hello World 2";
	println(str);
  myClass.print;
  
  def seekHelp(friend:Friend)=friend.listen;
  
  def getWeatherInfo(id:String)={
    val url="http://weather.yahooapis.com/forecastrss?p=" + id + "&u=c";
    val response = scala.io.Source.fromURL(url).mkString;
    val xmlResponse = XML.loadString(response);
    println(xmlResponse\\"location"\\"@city",xmlResponse\\"condition"\\"@temp");
  }
  
  println(myClass.total(List(1,2,3,4,5)));
  println(myClass.totalSelectValues(List(1,2,3,4,5,6),{e => e%2 == 0}));
  println(myClass.totalSelectValues(List(1,2,3,4,5,6),{e => e>4}));
  
  val peter=new Human("Peter");
  peter.listen();
  seekHelp(peter);
  
  val tommy=new Dog("Tommy");
  tommy.listen();
  seekHelp(tommy);
  
  val kitty=new Cat("Kitty") with Friend;
  kitty.listen();
  seekHelp(kitty);
  
  getWeatherInfo("INXX0012");
  
  val start=System.nanoTime();
  val caller=self;
  for(id <- 12 to 22){
    actor{caller ! getWeatherInfo("INXX00"+id.toString())};
  }
  val end=System.nanoTime();
  println("Time " + (end-start)/1000000000.0);
}
