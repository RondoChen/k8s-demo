package model

import (
	// "fmt"
	"github.com/labstack/echo/v4"

	// "time"
)

// UserContext godoc
// 用户信息
type UserContext struct {
	echo.Context
	User     *Account
	FormData *map[string]interface{}
}

// WrapError godoc
// 向页面输出一个错误信息
func (ctx *UserContext) WrapError(err error) error {
	return WrapError(ctx, err.Error())
}

// Wrap godoc
// 向页面输出一个正确结果
func (ctx *UserContext) Wrap(data interface{}) error {
	return Wrap(ctx, data)
}
