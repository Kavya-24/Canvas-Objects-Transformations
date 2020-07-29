package com.example.dancingrobot


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


class MyDrawView3D(context: Context?) :
    View(context, null) {


    val blue = getPaint(Color.BLUE)
    val pink = getPaint(Color.MAGENTA)
    val green = getPaint(Color.GREEN)
    val yellow = getPaint(Color.YELLOW)
    val cyan = getPaint(Color.CYAN)
    val black = getPaint(Color.BLACK)
    val grey = getPaint(Color.GRAY)
    val red = getPaint(Color.RED)
    val white = getPaint(Color.WHITE)
    val transparent = getPaint(Color.TRANSPARENT)

    private val TAG = MyDrawView3D::class.java.simpleName
    val thisview: MyDrawView3D = this


    private fun convertToRadians(degree: Double): Double {
        val pi = Math.PI
        val toRad = (degree * pi) / 180
        return toRad

    }

    private fun convertToDegrees(rad: Double): Double {
        val pi = Math.PI
        val toDeg = (rad * 180) / pi
        return toDeg
    }

    private fun DrawLinePairs(
        canvas: Canvas,
        vertices: Array<Coordinate?>,
        start: Int,
        end: Int,
        paint: Paint
    ) {

        //draw a line connecting 2 points
        //canvas - canvas of the view
        //points - array of points
        //start - index of the starting point
        //end - index of the ending point
        //paint - the paint of the line
        canvas.drawLine(
            vertices[start]!!.x.toFloat(),
            vertices[start]!!.y.toFloat(),
            vertices[end]!!.x.toFloat(),
            vertices[end]!!.y.toFloat(),
            paint
        )
    }

    private fun DrawCube(
        canvas: Canvas,
        draw_cube_vertices: Array<Coordinate?>,
        paint: Paint
    ) { //draw a cube on the screen
        DrawLinePairs(canvas, draw_cube_vertices, 0, 1, paint)
        DrawLinePairs(canvas, draw_cube_vertices, 1, 3, paint)
        DrawLinePairs(canvas, draw_cube_vertices, 3, 2, paint)
        DrawLinePairs(canvas, draw_cube_vertices, 2, 0, paint)
        DrawLinePairs(canvas, draw_cube_vertices, 4, 5, paint)
        DrawLinePairs(canvas, draw_cube_vertices, 5, 7, paint)
        DrawLinePairs(canvas, draw_cube_vertices, 7, 6, paint)
        DrawLinePairs(canvas, draw_cube_vertices, 6, 4, paint)
        DrawLinePairs(canvas, draw_cube_vertices, 0, 4, paint)
        DrawLinePairs(canvas, draw_cube_vertices, 1, 5, paint)
        DrawLinePairs(canvas, draw_cube_vertices, 2, 6, paint)
        DrawLinePairs(canvas, draw_cube_vertices, 3, 7, paint)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas) {
        //draw objects on the screen
        super.onDraw(canvas)


        val head = createFace(9.0, 9.0, 2.0, 60.0, 60.0, 60.0)
        runTask(canvas, head, blue)

        val neck = createFace(13.5, 16.0, 2.0, 40.0, 40.0, 60.0)
        DrawCube(canvas, neck, pink)

        val body = createCuboidFaceYMajor(5.4, 10.5, 2.0, 100.0, 80.0, 80.0)
        //val newBody = rotateY(body, 45.0)
        DrawCube(canvas, body, red)


        val leftHand = createCuboidFaceYMajor(13.6, 19.0, 2.0, 30.0, 40.0, 40.0)
        DrawCube(canvas, leftHand, blue)

        val rightHand = createCuboidFaceYMajor(22.3, 19.0, 2.0, 30.0, 40.0, 40.0)
        DrawCube(canvas, rightHand, blue)

        val leftElbow = createCuboidFaceYMajor(13.6, 23.0, 2.0, 30.0, 40.0, 40.0)
        DrawCube(canvas, leftElbow, green)

        val rightElbow = createCuboidFaceYMajor(22.3, 23.0, 2.0, 30.0, 40.0, 40.0)
        DrawCube(canvas, rightElbow, green)

        val leftWrist = createCuboidFaceYMajor(13.6, 98.0, 2.0, 30.0, 10.0, 40.0)
        DrawCube(canvas, leftWrist, cyan)

        val rightWrist = createCuboidFaceYMajor(22.3, 98.0, 2.0, 30.0, 10.0, 40.0)
        DrawCube(canvas, rightWrist, cyan)


        val beltArea = createCuboidFaceXMajor(10.8, 26.0, 2.0, 50.0, 40.0, 50.0)
        DrawCube(canvas, beltArea, pink)

        val leftLeg = createCuboidFaceYMajor(15.75, 29.0, 2.0, 30.0, 40.0, 40.0)
        DrawCube(canvas, leftLeg, blue)

        val rightLeg = createCuboidFaceYMajor(20.15, 29.0, 2.0, 30.0, 40.0, 40.0)
        DrawCube(canvas, rightLeg, blue)

        val leftFoot = createCuboidFaceYMajor(15.75, 33.0, 2.0, 30.0, 40.0, 40.0)
        DrawCube(canvas, leftFoot, green)

        val rightFoot = createCuboidFaceYMajor(20.15, 33.0, 2.0, 30.0, 40.0, 40.0)
        DrawCube(canvas, rightFoot, green)

        val leftShoe = createCuboidFaceYMajor(15.75, 142.0, 2.0, 30.0, 10.0, 40.0)
        DrawCube(canvas, leftShoe, red)

        val rightShoe = createCuboidFaceYMajor(20.15, 142.0, 2.0, 30.0, 10.0, 40.0)
        DrawCube(canvas, rightShoe, red)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun runTask(canvas: Canvas, part: Array<Coordinate?>, paint: Paint) {
        var currentAngle = 0.0
        var oldCoordinates = part
        var then = LocalDateTime.now()
        val timer = java.util.Timer()
        val now = LocalDateTime.now()
        val handler: Handler = Handler(Looper.getMainLooper())

        DrawCube(canvas, part, paint)
        val u = rotateY(part, 10.0)


        if (ChronoUnit.SECONDS.between(now, LocalDateTime.now()) >= 5) {
            DrawCube(canvas, part, transparent)
            DrawCube(canvas, u, paint)

        }
        val u2 = rotateY(u, 10.0)

        if (ChronoUnit.SECONDS.between(now, LocalDateTime.now()) >= 10) {
            DrawCube(canvas, u2, transparent)
            DrawCube(canvas, u2, paint)

        }


//        val then: LocalDateTime = LocalDateTime.now()
//
//        while (true) {
//
//            if (currentAngle >= 360.0) {
//                currentAngle = 0.0
//
//            }
//            if (ChronoUnit.SECONDS.between(then, LocalDateTime.now()) >= 20) {
//                break
//            }
//
//            canvas.save()
//
//            DrawCube(canvas, oldCoordinates, transparent)
//            val newCoordinates = rotateY(oldCoordinates, currentAngle)
//            DrawCube(canvas, newCoordinates, paint)
//            oldCoordinates = newCoordinates
//            currentAngle += 5.0
//
//            canvas.restore()
//            thisview.invalidate()
//
//
//        }

    }


    //*********************************
//matrix and transformation functions
    fun GetIdentityMatrix(): DoubleArray { //return an 4x4 identity matrix
        val matrix = DoubleArray(16)
        matrix[0] = 1.0
        matrix[1] = 0.0
        matrix[2] = 0.0
        matrix[3] = 0.0
        matrix[4] = 0.0
        matrix[5] = 1.0
        matrix[6] = 0.0
        matrix[7] = 0.0
        matrix[8] = 0.0
        matrix[9] = 0.0
        matrix[10] = 1.0
        matrix[11] = 0.0
        matrix[12] = 0.0
        matrix[13] = 0.0
        matrix[14] = 0.0
        matrix[15] = 1.0
        return matrix
    }

    fun getXRotation(angle: Double): DoubleArray {
        val matrix = GetIdentityMatrix()
        val rad = convertToRadians(angle)
        val cos = Math.cos(rad)
        val sin = Math.sin(rad)

        matrix[5] = cos
        matrix[6] = -sin
        matrix[9] = sin
        matrix[10] = cos
        return matrix
    }

    fun getZRotation(angle: Double): DoubleArray {
        val matrix = GetIdentityMatrix()
        val rad = convertToRadians(angle)
        val cos = Math.cos(rad)
        val sin = Math.sin(rad)

        matrix[0] = cos
        matrix[1] = -sin
        matrix[4] = sin
        matrix[5] = cos
        return matrix
    }

    fun getYRotation(angle: Double): DoubleArray {
        val matrix = GetIdentityMatrix()
        val rad = convertToRadians(angle)
        val cos = Math.cos(rad)
        val sin = Math.sin(rad)

        matrix[0] = cos
        matrix[2] = sin
        matrix[8] = -sin
        matrix[10] = cos
        return matrix
    }


    fun Transformation(
        vertex: Coordinate?,
        matrix: DoubleArray
    ): Coordinate { //affine transformation with homogeneous coordinates
        //i.e. a vector (vertex) multiply with the transformation matrix
        // vertex - vector in 3D
        // matrix - transformation matrix
        val result = Coordinate()
        result.x =
            matrix[0] * vertex!!.x + matrix[1] * vertex.y + matrix[2] * vertex.z + matrix[3]
        result.y =
            matrix[4] * vertex.x + matrix[5] * vertex.y + matrix[6] * vertex.z + matrix[7]
        result.z =
            matrix[8] * vertex.x + matrix[9] * vertex.y + matrix[10] * vertex.z + matrix[11]
        result.w =
            matrix[12] * vertex.x + matrix[13] * vertex.y + matrix[14] * vertex.z + matrix[15]
        return result
    }

    fun RotationalMatrix(w: Double, x: Double, y: Double, z: Double): DoubleArray {
        val quaterion = DoubleArray(16)

        quaterion[0] = w * w + x * x - y * y - z * z
        quaterion[1] = 2 * (x * y - w * z)
        quaterion[2] = 2 * (x * z + w * y)
        quaterion[3] = 0.0
        quaterion[4] = 2 * (x * y + w * z)
        quaterion[5] = w * w - x * x + y * y - z * z
        quaterion[6] = 2 * (y * z - x * w)
        quaterion[7] = 0.0
        quaterion[8] = 2 * (x * z - w * y)
        quaterion[9] = 2 * (y * z + x * w)
        quaterion[10] = w * w - x * x - y * y + z * z
        quaterion[11] = 0.0
        quaterion[12] = 0.0
        quaterion[13] = 0.0
        quaterion[14] = 0.0
        quaterion[15] = 1.0
        return quaterion
    }

    fun rotateAboutAxis(
        w: Double,
        x: Double,
        y: Double,
        z: Double,
        vertex: Coordinate
    ): Coordinate {
        val matrix = RotationalMatrix(w, x, y, z)
        vertex.w = 0.0
        val res = Transformation(vertex, matrix)
        return res

    }

    fun Transformation(
        vertices: Array<Coordinate?>,
        matrix: DoubleArray
    ): Array<Coordinate?> {   //Affine transform a 3D object with vertices
        // vertices - vertices of the 3D object.
        // matrix - transformation matrix
        val result = arrayOfNulls<Coordinate>(vertices.size)
        for (i in vertices.indices) {
            result[i] = Transformation(vertices[i], matrix)
            result[i]!!.Normalise()
        }
        Log.e("Val", result.toList().toString())
        return result
    }

    //***********************************************************
//Affine transformation
    fun translate(
        vertices: Array<Coordinate?>,
        tx: Double,
        ty: Double,
        tz: Double
    ): Array<Coordinate?> {
        val matrix = GetIdentityMatrix()
        matrix[3] = tx
        matrix[7] = ty
        matrix[11] = tz
        return Transformation(vertices, matrix)
    }

    private fun scale(
        vertices: Array<Coordinate?>,
        sx: Double,
        sy: Double,
        sz: Double
    ): Array<Coordinate?> {
        val matrix = GetIdentityMatrix()
        matrix[0] = sx
        matrix[5] = sy
        matrix[10] = sz
        return Transformation(vertices, matrix)
    }

    private fun rotateX(
        vertices: Array<Coordinate?>,
        angle: Double
    ): Array<Coordinate?> {

        val matrix = getXRotation(angle)
        return Transformation(vertices, matrix)

    }

    private fun rotateY(
        vertices: Array<Coordinate?>,
        angle: Double
    ): Array<Coordinate?> {

        val matrix = getYRotation(angle)
        return Transformation(vertices, matrix)

    }

    private fun rotateZ(
        vertices: Array<Coordinate?>,
        angle: Double
    ): Array<Coordinate?> {

        val matrix = getZRotation(angle)
        return Transformation(vertices, matrix)

    }


    fun getPaint(color: Int): Paint {
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.style = Paint.Style.STROKE //Stroke
        p.color = color
        p.strokeWidth = 2f
        return p
    }


    init {
        val thisview: MyDrawView3D = this
        thisview.invalidate() //update the view


    }

    private fun createIdentityCubeArrayCoordinates(): Array<Coordinate?> {
        val cube_vertices: Array<Coordinate?>
        cube_vertices = arrayOfNulls(8)
        cube_vertices[0] = Coordinate((-1).toDouble(), (-1).toDouble(), (-1).toDouble(), 1.0)
        cube_vertices[1] = Coordinate((-1).toDouble(), (-1).toDouble(), 1.0, 1.0)
        cube_vertices[2] = Coordinate((-1).toDouble(), 1.0, (-1).toDouble(), 1.0)
        cube_vertices[3] = Coordinate((-1).toDouble(), 1.0, 1.0, 1.0)
        cube_vertices[4] = Coordinate(1.0, (-1).toDouble(), (-1).toDouble(), 1.0)
        cube_vertices[5] = Coordinate(1.0, (-1).toDouble(), 1.0, 1.0)
        cube_vertices[6] = Coordinate(1.0, 1.0, (-1).toDouble(), 1.0)
        cube_vertices[7] = Coordinate(1.0, 1.0, 1.0, 1.0)

        return cube_vertices

    }

    private fun createIdentityCuboidYMajor(): Array<Coordinate?> {
        val cube_vertices: Array<Coordinate?>
        cube_vertices = arrayOfNulls(8)
        cube_vertices[0] = Coordinate((-1).toDouble(), (-2).toDouble(), (-1).toDouble(), 1.0)
        cube_vertices[1] = Coordinate((-1).toDouble(), (-2).toDouble(), 1.0, 1.0)
        cube_vertices[2] = Coordinate((-1).toDouble(), 2.0, (-1).toDouble(), 1.0)
        cube_vertices[3] = Coordinate((-1).toDouble(), 2.0, 1.0, 1.0)
        cube_vertices[4] = Coordinate(1.0, (-2).toDouble(), (-1).toDouble(), 1.0)
        cube_vertices[5] = Coordinate(1.0, (-2).toDouble(), 1.0, 1.0)
        cube_vertices[6] = Coordinate(1.0, 2.0, (-1).toDouble(), 1.0)
        cube_vertices[7] = Coordinate(1.0, 2.0, 1.0, 1.0)

        return cube_vertices

    }

    private fun createIdentityCuboidXMajor(): Array<Coordinate?> {
        val cube_vertices: Array<Coordinate?>
        cube_vertices = arrayOfNulls(8)
        //Cuboid 2
        cube_vertices[0] = Coordinate((-2).toDouble(), (-1).toDouble(), (-1).toDouble(), 1.0)
        cube_vertices[1] = Coordinate((-2).toDouble(), (-1).toDouble(), 1.0, 1.0)
        cube_vertices[2] = Coordinate((-2).toDouble(), 1.0, (-1).toDouble(), 1.0)
        cube_vertices[3] = Coordinate((-2).toDouble(), 1.0, 1.0, 1.0)
        cube_vertices[4] = Coordinate(2.0, (-1).toDouble(), (-1).toDouble(), 1.0)
        cube_vertices[5] = Coordinate(2.0, (-1).toDouble(), 1.0, 1.0)
        cube_vertices[6] = Coordinate(2.0, 1.0, (-1).toDouble(), 1.0)
        cube_vertices[7] = Coordinate(2.0, 1.0, 1.0, 1.0)

        return cube_vertices


    }

    private fun createFace(
        tx: Double,
        ty: Double,
        tz: Double,
        sx: Double,
        sy: Double,
        sz: Double
    ): Array<Coordinate?> {
        var draw_cube_vertices: Array<Coordinate?>
        val cube_vertices = createIdentityCubeArrayCoordinates()
        draw_cube_vertices = translate(cube_vertices, tx, ty, tz)
        draw_cube_vertices = scale(draw_cube_vertices, sx, sy, sz)
        return draw_cube_vertices
    }

    private fun createCuboidFaceXMajor(
        tx: Double,
        ty: Double,
        tz: Double,
        sx: Double,
        sy: Double,
        sz: Double
    ): Array<Coordinate?> {
        var draw_cube_vertices: Array<Coordinate?>
        val cube_vertices = createIdentityCuboidXMajor()
        draw_cube_vertices = translate(cube_vertices, tx, ty, tz)
        draw_cube_vertices = scale(draw_cube_vertices, sx, sy, sz)
        return draw_cube_vertices
    }

    private fun createCuboidFaceYMajor(
        tx: Double,
        ty: Double,
        tz: Double,
        sx: Double,
        sy: Double,
        sz: Double
    ): Array<Coordinate?> {
        var draw_cube_vertices: Array<Coordinate?>
        val cube_vertices = createIdentityCuboidYMajor()
        draw_cube_vertices = translate(cube_vertices, tx, ty, tz)
        draw_cube_vertices = scale(draw_cube_vertices, sx, sy, sz)
        return draw_cube_vertices
    }


}


