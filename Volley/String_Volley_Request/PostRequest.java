 JSONObject postRequestBody = new JSONObject();

        try {
            postRequestBody.put("PARAM", getPARAMFromYourCode);
                .
                .
                .
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, YOUR_URL_HERE, postRequestBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            if (response.getInt("status") == 1) {

                                //YOUR DESIRE ACTION CODE IN HERE

                            } else {

                                 //YOUR DESIRE ACTION CODE IN HERE

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        System.out.println("Save location error");
                    }
                }) { //If the Authentication URL needs Headers, use this:
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        //USE THE SINGLETON TO CREATE THE REQUEST
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);