import scala.io.StdIn.readLine
import scala.util.control.Breaks._
import org.apache.commons.cli._

object replTest {

    //represents a non directory file(txt)
    class Thing{
        var fileName:String = ""
        var content:String = ""
        var storage:Int = content.length
    }
    //represents a file directory
    class Location{
        var locationName:String = ""
        var items:List[Thing] = List()
        var pathways:List[Location] = List()
        var parentDirectory:Location = null
        var storageSize:Int = 10000
    }
    
     //base class initialization
        //-----------------------
        val test:Thing = new Thing
        test.fileName = "test.txt"
        test.content = "this is a test!"
        //really messy fix this/automate it
        val home:Location = new Location
        home.locationName = "jbauer" 
        val root:Location = new Location
        root.locationName = "/"
        home.parentDirectory = root
        root.pathways = List(home)
        //-----------------------

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

    def main(args:Array[String]):Unit =  {
        

        //player initialization
        //-----------------------
        val player = new player
        player.name = "Julian"
        player.current_working_directory = root
        player.home_directory = home
        //-----------------------
        var using = true

        val options:Options = new Options();
        val parser = new DefaultParser()
        
        
        options.addOption("exit", "e", false, "Allows user to exit program")
        //println(options)
        val data = readLine()
        val store = data.split(" ")
        
        val cmd:CommandLine = parser.parse(options,store)
        if(cmd.hasOption("exit") || cmd.hasOption("e")){
            println("works!")
        }

        }
    }


