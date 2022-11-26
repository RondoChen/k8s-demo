<?php

use Illuminate\Support\Facades\Route;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Http;


Route::get('/', function () {
    return "This is review servie";
});


Route::get('/apis/product-review/list/{productId}/{pageNum}/{pageSize}/', function ($productId, $pageNum,$pageSize) {
    $offset=($pageNum-1)*$pageSize;
    $sqlQuery = "select * from `review` where `product_id`=? order by `review_id` desc limit ? offset ?";
    $sqlCount = "select count(*) as CT from `review` where `product_id`=?";

    $reviews = DB::select($sqlQuery, [$productId, $pageSize, $offset]);
    $reviewsCount = DB::select($sqlCount, [$productId]);
    $count = $reviewsCount[0];
    return response()->json([
        'data' => [
            'items' => $reviews,
            'total' => ((array)$count)['CT']
        ],
        'success' => TRUE
    ]);
});



Route::post('/apis/product-review/post/', function (Request $request) {

    $b3_keys = ["x-request-id","x-b3-traceid", "x-b3-spanid", 
                "x-b3-parentspanid", "x-b3-sampled","x-b3-flags",
                "x-ot-span-context"];
    $all_headers = $request->header();
    //  return $all_headers;
    $matchHeaders = [];

    foreach($all_headers as $key => $value)  {
        if (in_array($key, $b3_keys)){
            $matchHeaders[$key] = $value;
        }
    }

    $data = $request->all();
    $content = $data["content"];
    $score = $data["score"];
    $ticket = $data["ticket"];
    $productId = $data["productId"];

    $userInfoUrl = env("SERVICE_PASSPORT")."/open/account/info?ticket=" . $ticket;


    $response = Http::withHeaders($matchHeaders)->get($userInfoUrl);

    $data = $response->json();
    $success = $data["success"];
    if ($success){
        $openId = $data["data"]["open_id"];
        $nick = $data["data"]["nick"];

        DB::table("review")->insert([
            'open_id' => $openId,
            'product_id' => $productId,
            'score' => $score,
            'review_user_name' => $nick,
            'content' => $content,
            'create_date'=> new DateTime()
        ]);

        return response()->json([
            'success' => TRUE
        ]);

    }else{
        return response()->json([
            'error' => [
                'message'=>'获取用户失败'
            ],
            'success' => FALSE
        ]);

    }
    return response()->json([
        'success' => TRUE
    ]);
});




// Route::get('/', function () {
//     return view('welcome');
// });


// Route::get('/', function () {
//     return view('welcome');
// });

