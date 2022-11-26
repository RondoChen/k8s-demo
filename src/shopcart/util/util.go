package util

import (
	"fmt"
	"io"
	"io/ioutil"
	"net/http"
)
import (
	"github.com/go-redis/redis/v7"
	"github.com/labstack/echo/v4"
)
import (
	"shopcart/config"
)

var redisClient *redis.Client

func init() {
	fmt.Println("Redis is connecting to", config.AppConfig.Redis["address"], "db", config.AppConfig.Redis["db"])
	// fmt.Println("my name:", config.AppConfig.Name)
	redisClient = redis.NewClient(&redis.Options{
		Addr:     config.AppConfig.Redis["address"].(string),
		Password: config.AppConfig.Redis["password"].(string), // no password set
		DB:       int(config.AppConfig.Redis["db"].(int)),     // use default DB
	})
}

// func getRedisClient() *redis.Client {
// 	return redis.NewClient(&redis.Options{
// 		Addr:     "localhost:6379",
// 		Password: "", // no password set
// 		DB:       0,  // use default DB
// 	})
// }

// ReadRedis godoc
// 读取 redis
func ReadRedis(key string) interface{} {

	// client := getRedisClient()
	val, err := redisClient.Get(key).Result()
	if err != nil {
		return nil
	}

	return val
}

// WriteRedis godoc
// 存储 redis
func WriteRedis(key string, val string) (bool, error) {

	// client := getRedisClient()
	err := redisClient.Set(key, val, 0).Err()
	if err != nil {
		return false, err
	}

	return true, nil
}

// Fetch godoc
// 通用远程调用方法
func Fetch(ctx echo.Context, method, service, url string, body io.Reader) (string, error) {
	headers := ctx.Request().Header
	client := &http.Client{}
	//提交请求
	host := config.AppConfig.Providers[service]
	// fmt.Println("远程访问：", host+url)
	reqest, err := http.NewRequest(method, host+url, body)
	if err != nil {
		return "", err
	}
	//pass through headers
	for k, v := range headers {
		if v != nil && len(v) > 0 {
			reqest.Header.Add(k, v[0])
		}
	}
	//处理返回结果
	response, err := client.Do(reqest)
	if err != nil {
		return "", err
	}
	defer response.Body.Close()

	bytes, err := ioutil.ReadAll(response.Body)
	if err != nil {
		return "", err
	}

	strBody := string(bytes[:])

	return strBody, nil
}

// // SaveItem 接受 2 个参数，一个是 key，一个是商品信息
// func SaveItem(key string, item map[string]interface{}) int {

// 	client := redis.NewClient(&redis.Options{
// 		Addr:     "localhost:6379",
// 		Password: "",
// 		DB:       0,
// 	})

// 	var inInterface map[string]interface{}
// 	inrec, _ := json.Marshal(item)
// 	json.Unmarshal(inrec, &inInterface)
// 	client.HSet(key, inInterface)
// 	return 0
// }

// // GetItem 获取购物车
// func GetItem(key string) (interface{}, error) {

// 	client := redis.NewClient(&redis.Options{
// 		Addr:     "localhost:6379",
// 		Password: "", // no password set
// 		DB:       0,  // use default DB
// 	})
// 	val, err := client.HGetAll(key).Result()
// 	if err != nil {
// 		return nil, err
// 	}

// 	// item := new(model.ShopcartItem)
// 	// if err := mapstructure.Decode(val, item); err != nil {
// 	// 	return nil, err
// 	// }
// 	// fmt.Println(val, err, key)
// 	return val, nil
// }
