//REQUEST METHOD GET


StringRequest stringRequest = new StringRequest(Request.Method.GET, YOUR_URL_HERE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);

                        try {

                            removeSimpleProgressDialog();

                            JSONObject obj = new JSONObject(response);
                            //if(obj.optString("status").equals("true")){
                            if (obj.getInt("status") == 1) {

                                dataModelArrayList = new ArrayList<>();
                                JSONArray dataArray = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    //for using this type of request, you need to create a data model to parse
                                    //the information into whatever widget you use: textview, recyclerview, imageview, etc.

                                    DataModel dataModel = new LocationDataModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    param = dataobj.getString("paramName");

                                    DataModel.setParam(param);
                                        .
                                        .
                                        .

                                    dataModelArrayList.add(dataModel);
                                }

                                setupRecycler();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Err: ", error.getMessage());
                    }
                }) {//If the Authentication URL needs Headers, use this:
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Token " + token);
                return headers;
            }
        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);