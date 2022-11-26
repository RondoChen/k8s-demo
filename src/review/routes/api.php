<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});


Route::get('/db', function(){
    // return $_ENV;

    // die("db conn: " . $_ENV["DB_USERNAME"]);
    // die("db conn: " . getenv("DB_USERNAME") . getenv('SERVICE_PRODUCT'));
    
     $users = DB::select('select * from review limit 20');

    return $users;
});
