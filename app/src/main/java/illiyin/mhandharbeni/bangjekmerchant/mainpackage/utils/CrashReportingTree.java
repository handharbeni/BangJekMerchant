package illiyin.mhandharbeni.bangjekmerchant.mainpackage.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import timber.log.Timber;

public class CrashReportingTree extends Timber.Tree  {
    @Override protected void log(int priority, String tag, @NonNull String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }

        CrashLibrary.log(priority, tag, message);

        if (t != null) {
            if (priority == Log.ERROR) {
                CrashLibrary.logError(t);
            } else if (priority == Log.WARN) {
                CrashLibrary.logWarning(t);
            }
        }
    }
}
