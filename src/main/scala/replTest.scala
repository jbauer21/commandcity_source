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
        val testFile:Location = new Location
        testFile.locationName = "test"
        val nothing:Location = new Location
        testFile.pathways = List(nothing)
        testFile.parentDirectory = home
        home.pathways = List(testFile)
        //-----------------------

    class player  {
        //name
        var name:String = ""
        //where the player is currently located within the file system
        var current_working_directory:Location = root
        var home_directory:Location = null

        def cd(s:String, root:Location):Unit = {
            val store = s.split("/").filterNot(_ == "")
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
                    //absolute pathing
                    if(s.charAt(0) == '/'){
                        //only difference between absolute and relative pathing: pwd begins from the root.
                        current_working_directory = root
                        breakable{
                            for(i<-store){
                                if(i == ".." && current_working_directory.parentDirectory != null){
                                    current_working_directory = current_working_directory.parentDirectory
                                }
                                else if(i != ".."){
                                for(j <- current_working_directory.pathways){
                                    if(i == j.locationName){
                                        current_working_directory = j
                                    }
                                else if(i == "."){
                                    current_working_directory = current_working_directory
                                }
                                    else{
                                        println("Directory not found: " + s)
                                        break()
                                    }
                                }
                                }
                            }
                            }
                        }
                    
                    //adding this comment to make sure that this file can be uploaded to git from my new laptop
                    //relative pathing
                    else{
                        breakable{
                            for(i<-store){
                                if(i == ".." && current_working_directory.parentDirectory != null){
                                    current_working_directory = current_working_directory.parentDirectory
                                }
                                else if(i != ".."){
                                for(j <- current_working_directory.pathways){
                                    if(i == j.locationName){
                                        current_working_directory = j
                                    }
                                else if(i == "."){
                                    current_working_directory = current_working_directory
                                }
                                    else{
                                        println("Directory not found: " + s)
                                        break()
                                    }
                                }
                                }
                                }
                            }
                    }
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

         while(using){
            val data = readLine
            //store should never be more than 2 long. I.E. "cd /test", only two arguments.
            val store = data.split(" ")
            val command = store(0)
            
            command match{
                case "cd" => {var info:String = store(1); player.cd(info, root)}
                case "pwd" => {println(player.current_working_directory.locationName)}
                case "ls" => {player.ls}
                case "exit" => {using = false}
                case _ => {println(command + " is not a valid command!")}
            }

        }
    }


}