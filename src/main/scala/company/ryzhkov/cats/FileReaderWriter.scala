package company.ryzhkov.cats

import cats.effect.IO
import cats.effect.Resource
import java.io.PrintWriter

object FileReaderWriter {

  def write(file: String, lines: Seq[String]) = {
    createWriter(file)
      .use { writer =>
        IO {
          for (line <- lines) writer.println(line)
        }
      }
  }

  def createWriter(file: String): Resource[IO, PrintWriter] =
    Resource.make {
      IO(new PrintWriter(file))
    } { writer =>
      IO(writer.close())
        .handleErrorWith(e => IO(println(e.getMessage)))
    }
}
