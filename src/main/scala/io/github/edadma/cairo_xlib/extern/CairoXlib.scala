package io.github.edadma.cairo_xlib.extern

import io.github.edadma.libcairo.extern.LibCairo._
import io.github.edadma.xlib.extern.Xlib._

import scala.scalanative.unsafe._

@link("cairo")
@extern
object CairoXlib {

  def cairo_xlib_surface_create(dpy: Display,
                                drawable: Drawable,
                                visual: Visual,
                                width: CInt,
                                height: CInt): cairo_surface_tp = extern //49
  def cairo_xlib_surface_create_for_bitmap(dpy: Display,
                                           bitmap: Pixmap,
                                           screen: Screen,
                                           width: CInt,
                                           height: CInt): cairo_surface_tp                    = extern //56
  def cairo_xlib_surface_set_size(surface: cairo_surface_tp, width: CInt, height: CInt): Unit = extern //63
  def cairo_xlib_surface_set_drawable(surface: cairo_surface_tp, drawable: Drawable, width: CInt, height: CInt): Unit =
    extern //68
  def cairo_xlib_surface_get_display(surface: cairo_surface_tp): Display   = extern //74
  def cairo_xlib_surface_get_drawable(surface: cairo_surface_tp): Drawable = extern //77
  def cairo_xlib_surface_get_screen(surface: cairo_surface_tp): Screen     = extern //80
  def cairo_xlib_surface_get_visual(surface: cairo_surface_tp): Visual     = extern //83
  def cairo_xlib_surface_get_depth(surface: cairo_surface_tp): CInt        = extern //86
  def cairo_xlib_surface_get_width(surface: cairo_surface_tp): CInt        = extern //89
  def cairo_xlib_surface_get_height(surface: cairo_surface_tp): CInt       = extern //92

}
