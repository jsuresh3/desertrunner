package com.example.desertrunner;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public MainThread thread;
    private sprite runner;
    private sprite snake;
    private ImageView iw;
    private boolean jump;
    private boolean ship_down;
    private boolean fall;
    private int g;
    private int v;
    private int u;
    private int snakev;
    private boolean reset;
    private boolean bounds;
    private boolean endGame;
    private String score;
    private int ground;
    private sprite tile;
    private sprite tile2;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();

        ground=1700;
        runner = new sprite(BitmapFactory.decodeResource(getResources(),R.drawable.run_outline));
        g=10;
        v=30;
        runner.scale(2);
        snake = new sprite(BitmapFactory.decodeResource(getResources(),R.drawable.snake1));
        tile=new sprite(BitmapFactory.decodeResource(getResources(),R.drawable.groundtile));
        tile2=new sprite(BitmapFactory.decodeResource(getResources(),R.drawable.ground));

        snake.scale(3);
        fall=false;
        ship_down=false;
        runner.x=30;

        tile.y=1700;
        tile.scale(3);

        tile2.y=tile.height+tile.y;
        tile2.scale(3);
        runner.y=ground-(runner.height);

        snake.x=1080;
        snake.y=ground-(snake.height);
        snakev =20;
        score="0";
        jump=false;
        ship_down=true;
        fall=false;
        u=1;
        bounds=false;
        endGame=false;
        reset=false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            boolean reset = true;

                canvas.drawColor(Color.rgb(135, 206, 235));
                Paint paint = new Paint();
                paint.setColor(Color.rgb(250, 250, 0));
                canvas.drawCircle(100, 100, 40, paint);

                snake.draw(canvas);

            paint.setARGB(255,50,43,39);
            canvas.drawRect(0,1716,1080,2400,paint);


                for (int i=0;i<1200;i=i+tile.width)
            {
                tile.x = i;
                tile.draw(canvas);
                tile2.x=i;
                tile2.draw(canvas);
                tile2.y+=tile2.height;
                tile2.draw(canvas);
                tile2.y-=tile2.height;

            }
                runner.draw(canvas);



                paint.setColor(Color.RED);
                paint.setTextSize(70);
                canvas.drawText("Score: " + score, 200, 300, paint);

                if (bounds == true) {
                    canvas.drawText("END", 500, 1000, paint);
                    canvas.drawText("RESET", 500, 600, paint);


                                }

     }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        if(e.getAction()==0)
        {jump=true;}
        System.out.println(e.getRawX());
        System.out.println(e.getRawY());
        if(bounds==true&&(e.getRawY()>500&&e.getRawY()<800))
        {
        reset=true;
        }

        return true;
    }

    public void update() {
        if (bounds == false) {
            if (jump == true) {
                v -= u;
                runner.y -= v;
            }

            if (runner.y > (ground- runner.height+1)) {

                jump = false;
                runner.y = ground- runner.height;
                v = 30;
            }

            if (snake.x > -800) {
                snake.x -= snakev;


            }
            if(snake.x<=-800&& runner.y==ground- runner.height) {
                snake.x = 1080;
                int s;
                s=Integer.parseInt(score);
                s++;
                score= String.valueOf(s);
                snakev = (int)((Math.random()*30)+20);
            }

            if ((runner.xR() > (snake.xL())) && (runner.xL()+30 < snake.xR()-60)) {
                if ((runner.yB()+(snake.height/2)) >= snake.yT()) {
                    bounds = true;

                }

            } else {
                bounds = false;
                System.out.println("bound off");
            }


        }

        if(bounds==true&&reset==true)
        {
            runner.y=ground- runner.height;
            snake.x=-500;
            score="0";
            bounds=false;
            reset=false;
        }
        //System.out.println("runner"+runner.yB());
        //System.out.println("snake"+snake.yT());
    }

    }


