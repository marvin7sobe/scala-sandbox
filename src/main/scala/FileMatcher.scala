import java.io.File

object FileMatcher {
  private val filesFromCurrentDirectory = (new File(".")).listFiles()

  private def filesMatching(query: String, matcher: (String, String) => Boolean) = {
    for (file <- filesFromCurrentDirectory; if matcher(file.getName, query))
      yield file
  }

  //simplified version with one argument
  private def filesMatching(matcher: String => Boolean) = {
    for (file <- filesFromCurrentDirectory; if matcher(file.getName))
      yield file
  }

  def filesEnding(query: String) = {
    filesMatching(query, (fileName, query) => fileName.endsWith(query))
    //above string is the same as
    //filesMatching(query, _.endsWith(_))
    //first _ is used for the fileName and the second _ for the query
    //we can use underscores here because fileName is used firstly in the matcher of the filesMatching and query secondly
  }

  def filesContains(query: String) = {
    filesMatching(query, _.contains(_))
  }

  def filesRegexp(query: String) = {
    filesMatching(_.matches(query))
  }

}
