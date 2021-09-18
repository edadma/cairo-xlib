cairo-xlib
==========

![GitHub release (latest by date including pre-releases)](https://img.shields.io/github/v/release/edadma/cairo-xlib?include_prereleases) ![GitHub (Pre-)Release Date](https://img.shields.io/github/release-date-pre/edadma/cairo-xlib) ![GitHub last commit](https://img.shields.io/github/last-commit/edadma/cairo-xlib) ![GitHub](https://img.shields.io/github/license/edadma/cairo-xlib)

*cairo-xlib* provides Scala Native bindings for the [Cairo](https://www.cairographics.org/) [X Window System](https://www.x.org/releases/current/) 2D graphics rendering support using [XLib](https://www.x.org/releases/current/doc/libX11/libX11/libX11.html).

Overview
--------

The goal of this project is to provide an easy-to-use Scala Native bindings for the Cairo 2D graphics library XLib Surfaces support.  Currently, the great majority of XLib related Cairo functions are supported.  Also, the `cairo_xlib.XlibSurface` class extends `libcairo.Surface` so that this library is perfectly interoperable with `libcairo`.  By using this library as a dependency, you also get Scala Native bindings for XLib.  The XLib bindings are not complete, but work is ongoing.

The more "programmer friendly" part of this library is found in the `io.github.edadma.cairo_xlib` package. That's the only
package you need to import from, as seen in the example below. The other package in the library
is `io.github.edadma.cairo_xlib.extern` which provides for interaction with the *libcairo* and *xlib* C libraries using Scala Native interoperability elements from the so-call `unsafe` namespace. There are no public declarations in
the `io.github.edadma.cairo_xlib` package that use `unsafe` types in their parameter or return types, making it a pure
Scala bindings library. Consequently, you never have to worry about memory allocation or type conversions.

Usage
-----

To use this library, `libcairo2` (and `libx11`) needs to be installed:

```shell
sudo apt install libcairo2
```

Include the following in your `project/plugins.sbt`:

```sbt
addSbtPlugin("com.codecommit" % "sbt-github-packages" % "0.5.2")

```

Include the following in your `build.sbt`:

```sbt
resolvers += Resolver.githubPackages("edadma")

libraryDependencies += "io.github.edadma" %%% "cairo-xlib" % "0.1.0"

```

Use the following `import` statements in your code:

```scala
import io.github.edadma.cairo_xlib._
import io.github.edadma.libcairo._
import io.github.edadma.xlib._

```

Example
--------

This example creates a Window with a simple drawing in it.  However, this example also prints all keyboard, mouse and window redraw events, showing how to check for and interpret various X11 events.

```scala
import io.github.edadma.cairo_xlib._
import io.github.edadma.libcairo._
import io.github.edadma.xlib._

object Main extends App {

  val sfc = createX11Surface(500, 500)

  eventLoop(sfc)
  destroyX11Surface(sfc)

  def createX11Surface(width: Int, height: Int): XlibSurface = {
    val dsp: Display = openDisplay(null)

    if (dsp.isNull) {
      Console.err.println("can't open display")
      sys.exit(1)
    }

    val screen = dsp.defaultScreen
    val da     = dsp.createSimpleWindow(dsp.defaultRootWindow, 0, 0, width, height, 0, 0, 0)

    dsp.selectInput(
      da,
      ButtonPressMask | ButtonReleaseMask | ButtonMotionMask | PointerMotionMask | KeyPressMask | KeyReleaseMask | ExposureMask)
    dsp.mapWindow(da)

    val sfc = surfaceCreate(dsp, da, dsp.defaultVisual(screen), width, height)

    sfc.setSize(width, height)
    sfc
  }

  def eventLoop(sfc: XlibSurface): Unit = {
    val event = new Event

    while (sfc.getDisplay.nextEvent(event) == Success) {
      event.getType match {
        case `ButtonPress` =>
          println(s"button press: ${event.button.button}, ${event.button.x}, ${event.button.y}, ${event.button.time}")
        case `ButtonRelease` => println(s"button release: ${event.button.button}")
        case `MotionNotify`  => println(s"motion: ${event.motion.state}, ${event.motion.x}, ${event.motion.y}")
        case `KeyPress` =>
          val (keystr, keysym) = event.key.lookupString
          val keysymstr        = keysymToString(keysym)

          println(s"key press: $keystr, $keysym, $keysymstr")

          if (keysym == XK_Return) {
            event.destroy()
            return
          }
        case `KeyRelease` =>
          val (keystr, keysym) = event.key.lookupString
          val keysymstr        = keysymToString(keysym)

          println(s"key release: $keystr, $keysym, $keysymstr")
        case `Expose` =>
          println("redraw")
          drawX11Surface(sfc)
        case e => println(e)
      }
    }

    event.destroy()
  }

  def drawX11Surface(sfc: Surface): Unit = {
    val ctx = sfc.create

    ctx.setSourceRGB(.5, .5, .5)
    ctx.paint()
    ctx.moveTo(20, 20)
    ctx.lineTo(200, 400)
    ctx.lineTo(450, 100)
    ctx.lineTo(20, 20)
    ctx.setSourceRGB(0, 1, 0)
    ctx.fill()
    ctx.destroy()
  }

  def destroyX11Surface(sfc: XlibSurface): Unit = {
    val dsp: Display = openDisplay(null)

    sfc.destroy()
    dsp.closeDisplay
  }

}

```

Documentation
-------------

API documentation is forthcoming, however documentation for Cairo XLib Surfaces is
found [here](https://www.cairographics.org/manual/cairo-XLib-Surfaces.html), and for the current release of XLib [here](https://www.x.org/releases/current/doc/libX11/libX11/libX11.html).

License
-------

[ISC](https://github.com/edadma/cairo-xlib/blob/main/LICENSE)
