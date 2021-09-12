package io.github.edadma

import io.github.edadma.libcairo._
import io.github.edadma.libcairo.extern.LibCairo.cairo_surface_tp
import io.github.edadma.xlib._
import io.github.edadma.cairo_xlib.extern.{CairoXlib => lib}

package object cairo_xlib {

  implicit class XlibSurface(override val surface: cairo_surface_tp) extends Surface(surface) {

    def setSize(width: Int, height: Int): Unit = lib.cairo_xlib_surface_set_size(surface, width, height)

    def setDrawable(drawable: Drawable, width: Int, height: Int): Unit =
      lib.cairo_xlib_surface_set_drawable(surface, drawable, width, height)

    def getDisplay: Display = lib.cairo_xlib_surface_get_display(surface)

    def getDrawable: Drawable = lib.cairo_xlib_surface_get_drawable(surface)

    def getScreen: Screen = lib.cairo_xlib_surface_get_screen(surface)

    def getVisual: Visual = lib.cairo_xlib_surface_get_visual(surface)

    def getDepth: Int = lib.cairo_xlib_surface_get_depth(surface)

    def getWidth: Int = lib.cairo_xlib_surface_get_width(surface)

    def getHeight: Int = lib.cairo_xlib_surface_get_height(surface)

  }

  def surfaceCreate(dpy: Display, drawable: Drawable, visual: Visual, width: Int, height: Int): XlibSurface =
    lib.cairo_xlib_surface_create(dpy.ptr, drawable, visual.visual, width, height)

  def surfaceCreateForBitmap(dpy: Display, bitmap: Pixmap, screen: Screen, width: Int, height: Int): cairo_surface_tp =
    lib.cairo_xlib_surface_create_for_bitmap(dpy.ptr, bitmap, screen, width, height)

}
