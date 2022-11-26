package model

import (
	"encoding/json"
	"github.com/labstack/echo/v4"
	"net/http"
	"time"
)
import (
	"shopcart/config"
)

type Response struct {
	Service   string      `json:"service" xml:"service"`
	Data      interface{} `json:"data" xml:"data"`
	Error     interface{} `json:"error" xml:"error"`
	Success   bool        `json:"success" xml:"success"`
	Timestamp int64       `json:"timestamp" xml:"timestamp"`
}

// type PagerResponse struct {
// 	Service   string      `json:"service" xml:"service"`
// 	Data      interface{} `json:"data" xml:"data"`
// 	Success   bool        `json:"success" xml:"success"`
// 	Timestamp int64       `json:"timestamp" xml:"timestamp"`
// }

func WrapError(ctx echo.Context, data interface{}) error {
	res := &Response{
		Service: config.AppConfig.Name,
		Error: map[string]interface{}{
			"message": data,
		},
		Success:   false,
		Timestamp: time.Now().Unix() * 1000,
	}
	//这里可以判断 accept 是 json 或者 xml 而做响应输出
	return ctx.JSON(http.StatusOK, res)
}

func Wrap(ctx echo.Context, data interface{}) error {
	res := &Response{
		Service:   config.AppConfig.Name,
		Data:      data,
		Success:   true,
		Timestamp: time.Now().Unix() * 1000,
	}
	//这里可以判断 accept 是 json 或者 xml 而做响应输出
	return ctx.JSON(http.StatusOK, res)
}

func ConvertToResponse(str string) Response {
	if str == "" {
		panic("错误的字符串")
	}

	jsonBlob := []byte(str)
	var response Response
	err := json.Unmarshal(jsonBlob, &response)
	if err != nil {
		panic(err)
	}

	return response
}
