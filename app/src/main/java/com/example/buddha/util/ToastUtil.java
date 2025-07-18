package com.example.buddha.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buddha.R;


public class ToastUtil {
    private static boolean isShow = true;//默认显示
    private static Toast mToast = null;//全局唯一的Toast
    private static Toast mToast2 = null;//全局唯二的Toast
    private static Toast mToast3 = null;//全局唯二的Toast
    private static long oneTime = 0;
    private static long twoTime = 0;
    private static String oldMsg;

    /*private控制不应该被实例化*/
    private ToastUtil() {
        throw new UnsupportedOperationException("不能被实例化");
    }

    /**
     * 全局控制是否显示Toast
     *
     * @param isShowToast
     */
    public static void controlShow(boolean isShowToast) {
        isShow = isShowToast;
    }

    /**
     * 取消Toast显示
     */
    public void cancelToast() {
        if (isShow && mToast != null) {
            mToast.cancel();
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(final Context context, final CharSequence message) {
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                Activity mActivity = (Activity) context;
                if (isShow && null != context && !mActivity.isFinishing()) {

                    if (mToast != null) {
                        mToast.cancel();
                        mToast = null;
                    }

                    if (mToast == null) {
                        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                        mToast.show();
                        oneTime = System.currentTimeMillis();
                    } else {
                        twoTime = System.currentTimeMillis();
                        if (message.equals(oldMsg)) {
                            if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                                mToast.show();
                            }
                        } else {
                            oldMsg = (String) message;
                            mToast.setText(message);
                            mToast.show();
                        }
                    }
                    oneTime = twoTime;
//                    if (mToast == null) {
//                        mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//                    } else {
//                        mToast.setText(message);
//                    }
//                    mToast.show();
                }
            }
        });
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param resId   资源ID:getResources().getString(R.string.xxxxxx);
     */
    public static void showShort(Context context, int resId) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(resId);
            }
            mToast.show();
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(final Context context, final CharSequence message) {
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (isShow) {
                    if (mToast2 == null) {
                        mToast2 = Toast.makeText(context, message, Toast.LENGTH_LONG);
                    } else {
                        mToast2.setText(message);
                    }
                    mToast2.show();
                }
            }
        });
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param resId   资源ID:getResources().getString(R.string.xxxxxx);
     */
    public static void showLong(Context context, int resId) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
            } else {
                mToast.setText(resId);
            }
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration 单位:毫秒
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            mToast.show();
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param resId    资源ID:getResources().getString(R.string.xxxxxx);
     * @param duration 单位:毫秒
     */
    public static void show(Context context, int resId, int duration) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, resId, duration);
            } else {
                mToast.setText(resId);
            }
            mToast.show();
        }
    }

    /**
     * 自定义Toast的View
     *
     * @param context
     * @param message
     * @param duration 单位:毫秒
     * @param view     显示自己的View
     */
    public static void customToastView(Context context, CharSequence message, int duration, View view) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            if (view != null) {
                mToast.setView(view);
            }
            mToast.show();
        }
    }

    /**
     * 自定义Toast的位置
     *
     * @param context
     * @param message
     * @param duration 单位:毫秒
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void customToastGravity(final Context context, final CharSequence message, int duration, final int gravity, final int xOffset, final int yOffset) {
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (isShow)

                {
                    if (mToast3 == null) {
                        mToast3 = Toast.makeText(context, message, Toast.LENGTH_LONG);
                    } else {
                        mToast3.setText(message);
                    }
                    mToast3.setGravity(gravity, xOffset, yOffset);
                    mToast3.show();
                }
            }
        });
    }

    /**
     * 自定义带图片和文字的Toast，最终的效果就是上面是图片，下面是文字
     *
     * @param context
     * @param message
     * @param iconResId 图片的资源id,如:R.drawable.icon
     * @param duration
     * @param gravity
     * @param xOffset
     * @param yOffset
     */
    public static void showToastWithImageAndText(Context context, CharSequence message, int iconResId, int duration, int gravity, int xOffset, int yOffset) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            mToast.setGravity(gravity, xOffset, yOffset);
            LinearLayout toastView = (LinearLayout) mToast.getView();
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(iconResId);
            toastView.addView(imageView, 0);
            mToast.show();
        }
    }

    /**
     * 自定义Toast,针对类型CharSequence
     *
     * @param context
     * @param message
     * @param duration
     * @param view
     * @param isGravity        true,表示后面的三个布局参数生效,false,表示不生效
     * @param gravity
     * @param xOffset
     * @param yOffset
     * @param isMargin         true,表示后面的两个参数生效，false,表示不生效
     * @param horizontalMargin
     * @param verticalMargin
     */
    public static void customToastAll(Context context, CharSequence message, int duration, View view, boolean isGravity, int gravity, int xOffset, int yOffset, boolean isMargin, float horizontalMargin, float verticalMargin) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, message, duration);
            } else {
                mToast.setText(message);
            }
            if (view != null) {
                mToast.setView(view);
            }
            if (isMargin) {
                mToast.setMargin(horizontalMargin, verticalMargin);
            }
            if (isGravity) {
                mToast.setGravity(gravity, xOffset, yOffset);
            }
            mToast.show();
        }
    }

    /**
     * 自定义Toast,针对类型resId
     *
     * @param context
     * @param resId
     * @param duration
     * @param view             :应该是一个布局，布局中包含了自己设置好的内容
     * @param isGravity        true,表示后面的三个布局参数生效,false,表示不生效
     * @param gravity
     * @param xOffset
     * @param yOffset
     * @param isMargin         true,表示后面的两个参数生效，false,表示不生效
     * @param horizontalMargin
     * @param verticalMargin
     */
    public static void customToastAll(Context context, int resId, int duration, View view, boolean isGravity, int gravity, int xOffset, int yOffset, boolean isMargin, float horizontalMargin, float verticalMargin) {
        if (isShow) {
            if (mToast == null) {
                mToast = Toast.makeText(context, resId, duration);
            } else {
                mToast.setText(resId);
            }
            if (view != null) {
                mToast.setView(view);
            }
            if (isMargin) {
                mToast.setMargin(horizontalMargin, verticalMargin);
            }
            if (isGravity) {
                mToast.setGravity(gravity, xOffset, yOffset);
            }
            mToast.show();
        }
    }

    public static void ToastMessage(final Activity context, final String messages) {
        ThreadUtil.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (isShow) {
                    if (mToast == null) {
                        mToast = Toast.makeText(context, messages, Toast.LENGTH_LONG);
                        toastSetLyout(context, messages);
                    } else {
                        toastSetLyout(context, messages);
                    }
                    mToast.show();
                }
            }
        });
    }

    //自定义toast布局
    private static void toastSetLyout(Activity context, String messages) {
        //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
        LayoutInflater inflater = context.getLayoutInflater();//调用Activity的getLayoutInflater()
        View view = inflater.inflate(R.layout.toast_style, null); //加載layout下的布局
        TextView text = view.findViewById(R.id.tvTextToast);
        text.setText(messages); //toast内容
        mToast.setGravity(Gravity.CENTER, 0, 0);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        mToast.setView(view); //添加视图文件
    }

}