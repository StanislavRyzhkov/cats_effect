package company.ryzhkov.cats

import cats.effect.{IO, Resource}
import cats.implicits._
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.io.PrintWriter

object HttpClient {
  val query =
    // "https://www.quandl.com/api/v3/datatables/SHARADAR/SEP.csv?ticker=AAPL&qopts.columns=date,close,dividends&date.gte=2020-01-01&api_key=sMCfXgsrnJevqgA-G12G"
    // "https://www.quandl.com/api/v3/datatables/SHARADAR/SEP.csv?ticker=FAST&date.gte=2019-01-01&api_key=sMCfXgsrnJevqgA-G12G"
    // "https://www.quandl.com/api/v3/datatables/SHARADAR/SEP.csv?date=2020-01-22&api_key=sMCfXgsrnJevqgA-G12G"
    "https://www.quandl.com/api/v3/datatables/SHARADAR/TICKERS.csv?table=SF1&ticker=ZNGA&qopts.columns=ticker,name&api_key=sMCfXgsrnJevqgA-G12G"
  // "https://www.quandl.com/api/v3/datatables/SHARADAR/TICKERS.csv?table=SF1&qopts.columns=ticker,name&api_key=sMCfXgsrnJevqgA-G12G"

  // https://www.quandl.com/api/v3/datatables/SHARADAR/TICKERS.csv?qopts.export=true&qopts.columns=ticker,name&table=SF1&api_key=sMCfXgsrnJevqgA-G12G

  def main(args: Array[String]) = {
    val io  = proccess()
    val io2 = proccess2("1.csv")
    io.unsafeRunAsync {
      case Right(unit) => unit
      case Left(e)     => println(e.getMessage)
    }
  }

  def foo(in: BufferedReader): IO[Unit] =
    for {
      line <- IO(in.readLine())
      _ <- if (line != null) IO(println(line)) *> foo(in)
          else IO.unit
    } yield ()

  def foo2(in: BufferedReader, out: PrintWriter): IO[Unit] =
    for {
      line <- IO(in.readLine())
      _ <- if (line != null) IO(out.println(line)) *> foo2(in, out)
          else IO.unit
    } yield ()

  def proccess(): IO[Unit] = {
    (for {
      con    <- createURLConnection
      source <- createBufferedSource(con)
    } yield source)
      .use { in =>
        foo(in)
      }
  }

  def proccess2(file: String): IO[Unit] = {
    (for {
      con    <- createURLConnection
      source <- createBufferedSource(con)
      writer <- FileReaderWriter.createWriter(file)
    } yield (source, writer))
      .use { tuple =>
        foo2(tuple._1, tuple._2)
      }
  }

  def createURLConnection: Resource[IO, HttpURLConnection] =
    Resource.make {
      IO {
        val httpUrlConnection =
          new URL(query).openConnection
            .asInstanceOf[HttpURLConnection]
        httpUrlConnection.setRequestMethod("GET")
        httpUrlConnection.setUseCaches(false)
        httpUrlConnection.connect
        httpUrlConnection
      }
    } { connection =>
      IO(connection.disconnect())
        .handleErrorWith(e => IO(println(e.getMessage)))
    }

  def createBufferedSource(
      con: HttpURLConnection
  ): Resource[IO, BufferedReader] =
    Resource.make {
      IO {
        new BufferedReader(new InputStreamReader(con.getInputStream()))
      }
    } { reader =>
      IO(reader.close())
        .handleErrorWith(e => IO(println(e.getMessage)))
    }
}
