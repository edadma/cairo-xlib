//import io.github.edadma.cairo_xlib._
//import io.github.edadma.libcairo._
//import io.github.edadma.xlib._
//
//object Main extends App {
//
//  val sfc = createX11Surface(500, 500)
//
//  eventLoop(sfc)
//  destroyX11Surface(sfc)
//
//  def createX11Surface(width: Int, height: Int): XlibSurface = {
//    val dsp: Display = openDisplay(null)
//
//    if (dsp.isNull) {
//      Console.err.println("can't open display")
//      sys.exit(1)
//    }
//
//    val screen = dsp.defaultScreen
//    val da     = dsp.createSimpleWindow(dsp.defaultRootWindow, 0, 0, width, height, 0, 0, 0)
//
//    dsp.selectInput(
//      da,
//      ButtonPressMask | ButtonReleaseMask | ButtonMotionMask | PointerMotionMask | KeyPressMask | KeyReleaseMask | ExposureMask)
//    dsp.mapWindow(da)
//
//    val sfc = surfaceCreate(dsp, da, dsp.defaultVisual(screen), width, height)
//
//    sfc.setSize(width, height)
//    sfc
//  }
//
//  def eventLoop(sfc: XlibSurface): Unit = {
//    val event = new Event
//
//    while (sfc.getDisplay.nextEvent(event) == Success) {
//      event.getType match {
//        case `ButtonPress` =>
//          println(s"button press: ${event.button.button}, ${event.button.x}, ${event.button.y}, ${event.button.time}")
//        case `ButtonRelease` => println(s"button release: ${event.button.button}")
//        case `MotionNotify`  => println(s"motion: ${event.motion.state}, ${event.motion.x}, ${event.motion.y}")
//        case `KeyPress` =>
//          val (keystr, keysym) = event.key.lookupString
//          val keysymstr        = keysymToString(keysym)
//
//          println(s"key press: $keystr, $keysym, $keysymstr")
//
//          if (keysym == XK_Return) {
//            event.destroy()
//            return
//          }
//        case `KeyRelease` =>
//          val (keystr, keysym) = event.key.lookupString
//          val keysymstr        = keysymToString(keysym)
//
//          println(s"key release: $keystr, $keysym, $keysymstr")
//        case `Expose` =>
//          println("redraw")
//          drawX11Surface(sfc)
//        case e => println(e)
//      }
//    }
//
//    event.destroy()
//  }
//
//  def drawX11Surface(sfc: Surface): Unit = {
//    val ctx = sfc.create
//
//    ctx.setSourceRGB(.5, .5, .5)
//    ctx.paint()
//    ctx.moveTo(20, 20)
//    ctx.lineTo(200, 400)
//    ctx.lineTo(450, 100)
//    ctx.lineTo(20, 20)
//    ctx.setSourceRGB(0, 1, 0)
//    ctx.fill()
//    ctx.destroy()
//  }
//
//  def destroyX11Surface(sfc: XlibSurface): Unit = {
//    val dsp: Display = openDisplay(null)
//
//    sfc.destroy()
//    dsp.closeDisplay
//  }
//
//}
