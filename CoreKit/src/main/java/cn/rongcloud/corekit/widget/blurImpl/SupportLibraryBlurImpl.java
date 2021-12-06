/*
 * Copyright © 2021 RongCloud. All rights reserved.
 */

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.rongcloud.corekit.widget.blurImpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;


public class SupportLibraryBlurImpl implements BlurImpl {
    static Boolean DEBUG = null;
    private RenderScript mRenderScript;
    private ScriptIntrinsicBlur mBlurScript;
    private Allocation mBlurInput;
    private Allocation mBlurOutput;

    public SupportLibraryBlurImpl() {
    }

    static boolean isDebug(Context ctx) {
        if (DEBUG == null && ctx != null) {
            DEBUG = (ctx.getApplicationInfo().flags & 2) != 0;
        }

        return DEBUG == Boolean.TRUE;
    }

    public boolean prepare(Context context, Bitmap buffer, float radius) {
        if (this.mRenderScript == null) {
            try {
                this.mRenderScript = RenderScript.create(context);
                this.mBlurScript = ScriptIntrinsicBlur.create(this.mRenderScript, Element.U8_4(this.mRenderScript));
            } catch (RSRuntimeException var5) {
                if (isDebug(context)) {
                    throw var5;
                }

                this.release();
                return false;
            }
        }

        this.mBlurScript.setRadius(radius);
        this.mBlurInput = Allocation.createFromBitmap(this.mRenderScript, buffer, Allocation.MipmapControl.MIPMAP_NONE, 1);
        this.mBlurOutput = Allocation.createTyped(this.mRenderScript, this.mBlurInput.getType());
        return true;
    }

    public void release() {
        if (this.mBlurInput != null) {
            this.mBlurInput.destroy();
            this.mBlurInput = null;
        }

        if (this.mBlurOutput != null) {
            this.mBlurOutput.destroy();
            this.mBlurOutput = null;
        }

        if (this.mBlurScript != null) {
            this.mBlurScript.destroy();
            this.mBlurScript = null;
        }

        if (this.mRenderScript != null) {
            this.mRenderScript.destroy();
            this.mRenderScript = null;
        }

    }

    public void blur(Bitmap input, Bitmap output) {
        if (null != mBlurInput && null != mBlurScript && null != mBlurOutput) {
            this.mBlurInput.copyFrom(input);
            this.mBlurScript.setInput(this.mBlurInput);
            this.mBlurScript.forEach(this.mBlurOutput);
            this.mBlurOutput.copyTo(output);
        }
    }
}
