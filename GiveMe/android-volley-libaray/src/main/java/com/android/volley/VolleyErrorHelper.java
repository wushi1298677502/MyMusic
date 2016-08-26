package com.android.volley;

import android.content.Context;

public class VolleyErrorHelper {
    /**
     * Returns appropriate message which is to be displayed to the user against
     * the specified error object.
     *
     * @param error
     * @param context
     * @return
     */
    public static String getMessage(Object error, Context context) {
        if (error instanceof TimeoutError) {
            return context.getResources().getString(R.string.generic_server_down);
        } else if (isServerProblem(error)) {
            return handleServerError(error, context);
        } else if (isNetworkProblem(error)) {
            return context.getResources().getString(R.string.no_internet);
        } else if (isParserProblem(error)) {
            return context.getResources().getString(R.string.json_parser_error);
        }
        return context.getResources().getString(R.string.generic_error);
    }

    /**
     * Determines whether the error is related to network
     *
     * @param error
     * @return
     */
    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError)
                || (error instanceof NoConnectionError);
    }

    private static boolean isParserProblem(Object error) {
        return error instanceof ParseError;
    }

    /**
     * Determines whether the error is related to server
     *
     * @param error
     * @return
     */
    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError)
                || (error instanceof AuthFailureError);
    }

    /**
     * Handles the server error, tries to determine whether to show a stock
     * message or to show a message retrieved from the server.
     *
     * @param err
     * @param context
     * @return
     */
    private static String handleServerError(Object err, Context context) {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;

        if (response == null) {
            return context.getResources().getString(R.string.generic_error);
        }
        switch (response.statusCode) {
            case 400:
            case 404:
            case 422:
            case 401:
                return context.getResources().getString(R.string.generic_error);
            default:
                return context.getResources().getString(R.string.generic_server_down);
        }


    }
}