package middleware

import (
	// "fmt"
	"shopcart/model"
	"shopcart/util"

	"github.com/labstack/echo/v4"
	"github.com/mitchellh/mapstructure"
	// "shopcart/controller"
	// "strconv"
)

// UserContextHandle godoc
// 中间件
func UserContextHandle(next echo.HandlerFunc) echo.HandlerFunc {
	return func(c echo.Context) error {
		ticket := c.QueryParam("ticket")
		xmap := map[string]interface{}{}
		c.Bind(&xmap) //绑定 map
		if ticket == "" {
			ticket = xmap["ticket"].(string)
		}
		if ticket == "" {
			return model.WrapError(c, "ticket required")
		}
		userInfo, _ := util.Fetch(c, "GET", "passport", "/open/account/info?ticket="+ticket, nil)

		commonResponse := model.ConvertToResponse(userInfo)

		if !commonResponse.Success {
			return model.WrapError(c, commonResponse.Error)
		}
		var user model.Account
		err := mapstructure.Decode(commonResponse.Data, &user)
		if err != nil {
			return model.WrapError(c, err)
		}
		// fmt.Println("data", user)

		// account := accountResponse.Data
		// fmt.Println("XXXXXX", commonResponse.Data, user)

		ctx := &model.UserContext{
			Context:  c,
			User:     &user,
			FormData: &xmap,
		}

		// fmt.Println("这个是中间件，看看输出吧", ticket)
		return next(ctx)
	}
}
