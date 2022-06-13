import scala.io.StdIn.readLine
import scala.util.control.Breaks._

object replTest {

    //refers to any object that may be found within a location. Relegated soley to strings right now. 
    case class Object(fileName:String, content:String)
    case class Location(locationName:String, items:List[Object], pathways:List[Location], parentDirectory:Location)
    
    val test:Object = Object("test.txt", "stuff!!!!")
    //this is paradoxical. one can't reference the other since they both may contain initializations that haven't happened yet. 
    //ask Dr. Hibbs about how to fix this.
    //val testDirectory
    val home:Location = Location("jbauer", List(test), List(), root)
    val root:Location = Location("/", List(), List(home), null)
    class player  {
        //name
        var name:String = ""
        //where the player is currently located within the file system
        var current_working_directory:Location = root
        var home_directory:Location = null

        
        def cd(s:String):Unit = {

            s match{
                case "/" => {current_working_directory = root}
                case "~" => {current_working_directory = home_directory}
                case ".." => {
                    if(current_working_directory.parentDirectory != null){
                        current_working_directory = current_working_directory.parentDirectory
                    }
                    else{
                        current_working_directory = root
                    }
                } 
                case _ => {
                    
                }
                

            }
        }

        def ls():Unit = {
            for(i <- current_working_directory.pathways){
                println(i.locationName)
            }
            for(i <- current_working_directory.items){
                println(i.fileName)
            }
        }
    }

    def main(args:Array[String]):Unit ={
        //-----------------------
        val player = new player
        player.name = "Julian"
        player.current_working_directory = root
        player.home_directory = home
        //-----------------------
        var using = true

        //TEST
        //split stores a null variable if there's a slash at the beginning.
        //store all values in a new array without null values to get the accurate
        //array of pathways.
        var testy = "/jbauer/test/home"
        var arrtest = testy.split("/")
        //**************************

        println(arrtest.mkString(","))
        while(using){
            val data = readLine
            //store should never be more than 2 long. I.E. "cd /test", only two arguments.
            val store = data.split(" ")
            val command = store(0)
            
            command match{
                case "cd" => {var info:String = store(1); player.cd(info)}
                case "pwd" => {println(player.current_working_directory.locationName)}
                case "ls" => {player.ls}
                case "exit" => {using = false}
            }
            }

        }
    }


