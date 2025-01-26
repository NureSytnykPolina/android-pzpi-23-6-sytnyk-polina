package nure.sytnyk.polina.pract3;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.handlerMessageTextView);
        Button startHandlerButton = findViewById(R.id.startHandlerButton);
        Button startBackgroundThreadButton = findViewById(R.id.startBackgroundThreadButton);
        Button sendMessageWithHandlerButton = findViewById(R.id.sendMessageWithHandlerButton);
        Button startHandlerThreadButton2 = findViewById(R.id.startHandlerThreadButton);


        handler = new Handler(Looper.getMainLooper());


        startHandlerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Handler executed after delay");
                    }
                }, 2000);
            }
        });


        startBackgroundThreadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("Updated from background thread");
                            }
                        });
                    }
                }).start();
            }
        });

        // Кнопка для надсилання повідомлення через Handler
        sendMessageWithHandlerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler1 = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        textView.setText("Message received: " + msg.what);
                    }
                };

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Message msg = handler1.obtainMessage();
                        msg.what = 1;
                        handler1.sendMessage(msg);
                    }
                }).start();
            }
        });

        // Створення HandlerThread для більш складних фонових задач
        startHandlerThreadButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerThread handlerThread = new HandlerThread("BackgroundThread");
                handlerThread.start();
                Handler backgroundHandler = new Handler(handlerThread.getLooper());

                backgroundHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(4000); // Затримка 4 секунди
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("Updated from HandlerThread");
                            }
                        });
                    }
                });
            }
        });
    }
}
