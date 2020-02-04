package mud.per.iw.pcom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class imgclass extends AppCompatImageView {

    private float gmradius = 25.0f;
    private Path gmpath;
    private RectF gmrect;
    @Override
    protected void onDraw(Canvas canvas) {
        gmrect = new RectF(0, 0, this.getWidth(), this.getHeight());
        gmpath.addRoundRect(gmrect, gmradius, gmradius, Path.Direction.CW);
        canvas.clipPath(gmpath);
        super.onDraw(canvas);
    }
    public imgclass(Context context) {
        super(context);
        init();
    }
    private void init() {
        gmpath = new Path();

    }

    public imgclass(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public imgclass(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }



}
