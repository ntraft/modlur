package com.ntraft.util;

/**
 * Convenience access to Android's logger without having to specify the log tag
 * every goddamn time.
 *
 * @author Neil Traft
 */
public final class Log {

	private static String TAG = "com.ntraft";

	/**
	 * Priority constant for the println method; use Log.v.
	 */
	public static final int VERBOSE = android.util.Log.VERBOSE;

	/**
	 * Priority constant for the println method; use Log.d.
	 */
	public static final int DEBUG = android.util.Log.DEBUG;

	/**
	 * Priority constant for the println method; use Log.i.
	 */
	public static final int INFO = android.util.Log.INFO;

	/**
	 * Priority constant for the println method; use Log.w.
	 */
	public static final int WARN = android.util.Log.WARN;

	/**
	 * Priority constant for the println method; use Log.e.
	 */
	public static final int ERROR = android.util.Log.ERROR;

	/**
	 * Priority constant for the println method.
	 */
	public static final int ASSERT = android.util.Log.ASSERT;

	private Log() {
	}

	public static void setTag(String tag) {
		TAG = tag;
	}

	/**
	 * Send a {@link #VERBOSE} log message.
	 *
	 * @param msg The message you would like logged.
	 */
	public static int v(String msg) {
		return android.util.Log.v(TAG, msg);
	}

	/**
	 * Send a {@link #VERBOSE} log message and log the exception.
	 *
	 * @param msg The message you would like logged.
	 * @param tr  An exception to log
	 */
	public static int v(String msg, Throwable tr) {
		return android.util.Log.v(TAG, msg, tr);
	}

	/**
	 * Send a {@link #DEBUG} log message.
	 *
	 * @param msg The message you would like logged.
	 */
	public static int d(String msg) {
		return android.util.Log.d(TAG, msg);
	}

	/**
	 * Send a {@link #DEBUG} log message and log the exception.
	 *
	 * @param msg The message you would like logged.
	 * @param tr  An exception to log
	 */
	public static int d(String msg, Throwable tr) {
		return android.util.Log.d(TAG, msg, tr);
	}

	/**
	 * Send an {@link #INFO} log message.
	 *
	 * @param msg The message you would like logged.
	 */
	public static int i(String msg) {
		return android.util.Log.i(TAG, msg);
	}

	/**
	 * Send a {@link #INFO} log message and log the exception.
	 *
	 * @param msg The message you would like logged.
	 * @param tr  An exception to log
	 */
	public static int i(String msg, Throwable tr) {
		return android.util.Log.i(TAG, msg, tr);
	}

	/**
	 * Send a {@link #WARN} log message.
	 *
	 * @param msg The message you would like logged.
	 */
	public static int w(String msg) {
		return android.util.Log.w(TAG, msg);
	}

	/**
	 * Send a {@link #WARN} log message and log the exception.
	 *
	 * @param msg The message you would like logged.
	 * @param tr  An exception to log
	 */
	public static int w(String msg, Throwable tr) {
		return android.util.Log.w(TAG, msg, tr);
	}

	/**
	 * Checks to see whether or not a log for the specified tag is loggable at the specified level.
	 * <p/>
	 * The default level of any tag is set to INFO. This means that any level above and including
	 * INFO will be logged. Before you make any calls to a logging method you should check to see
	 * if your tag should be logged. You can change the default level by setting a system property:
	 * 'setprop log.tag.&lt;YOUR_LOG_TAG> &lt;LEVEL>'
	 * Where level is either VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT, or SUPPRESS. SUPRESS will
	 * turn off all logging for your tag. You can also create a local.prop file that with the
	 * following in it:
	 * 'log.tag.&lt;YOUR_LOG_TAG>=&lt;LEVEL>'
	 * and place that in /data/local.prop.
	 *
	 * @param level The level to check.
	 * @return Whether or not that this is allowed to be logged.
	 * @throws IllegalArgumentException is thrown if the tag.length() > 23.
	 */
	public static boolean isLoggable(int level) {
		return android.util.Log.isLoggable(TAG, level);
	}

	/**
	  * Send a {@link #WARN} log message and log the exception.
	  *
	  * @param tr An exception to log
	  */
	public static int w(Throwable tr) {
		return android.util.Log.w(TAG, tr);
	}

	/**
	 * Send an {@link #ERROR} log message.
	 *
	 * @param msg The message you would like logged.
	 */
	public static int e(String msg) {
		return android.util.Log.e(TAG, msg);
	}

	/**
	 * Send a {@link #ERROR} log message and log the exception.
	 *
	 * @param msg The message you would like logged.
	 * @param tr  An exception to log
	 */
	public static int e(String msg, Throwable tr) {
		return android.util.Log.e(TAG, msg, tr);
	}

	/**
	 * Handy function to get a loggable stack trace from a Throwable
	 *
	 * @param tr An exception to log
	 */
	public static String getStackTraceString(Throwable tr) {
		return android.util.Log.getStackTraceString(tr);
	}

	/**
	 * Low-level logging call.
	 *
	 * @param priority The priority/type of this log message
	 * @param msg      The message you would like logged.
	 * @return The number of bytes written.
	 */
	public static int println(int priority, String msg) {
		return android.util.Log.println(priority, TAG, msg);
	}
}
