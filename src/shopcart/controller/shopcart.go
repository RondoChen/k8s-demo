package controller

import (
	"fmt"
	"github.com/labstack/echo/v4"
	"shopcart/model"
	"shopcart/service"
	"shopcart/util"
	"strings"
)

// Home godoc
// @Summary 首页 API
// @Description 默认 API
// @Accept  json
// @Produce  json
// @Router / [get]
func Home(c echo.Context) error {
	// ctx := c.(*middleware.UserContext)
	return model.Wrap(c, "hello shopcart.")

}

// AddGoods godoc
// @Summary 将商品加入购物车
// @Description 将商品加入购物车，购物车分为匿名购物车和用户购物车。
// @Accept  json
// @Produce  json
// @Router /open/shopcart [put]
func AddGoods(c echo.Context) error {
	ctx := c.(*model.UserContext)
	// xmap := map[string]interface{}{}
	// if err := ctx.Bind(&xmap); err != nil {
	// 	return ctx.WrapError(err)
	// }
	// item := new(model.ShopcartItem)
	// fmt.Println(xmap)
	shopcartID := ctx.User.OpenID

	// shopcartID := xmap["shopcart_id"].(string) //shopcart_id 不能为空

	// delete(xmap, "shopcart_id")
	// delete(xmap, "ticket")

	xmap := ctx.FormData
	// cart, err2 := service.ChangeCart(shopcartID, *xmap)

	_, err := service.AddCart(shopcartID, *xmap)
	if err != nil {
		// panic(err)
		return ctx.WrapError(err)
	}

	// if err = ctx.Bind(item); err != nil {
	// 	return  model.WrapError(ctx, err)
	// }
	// fmt.Println(item)

	// util.SaveItem(shopcartID, xmap)

	return ctx.Wrap(true)
}

// GetCart godoc
// @Summary 从 redis 中获取 购物车信息
// @Description 将商品加入购物车，购物车分为匿名购物车和用户购物车。
// @Accept  json
// @Produce  json
// @Router /open/shopcart [get]
func GetCart(c echo.Context) (err error) {
	ctx := c.(*model.UserContext)
	shopcartID := ctx.User.OpenID
	shopcart := service.GetCart(shopcartID)

	if len(*shopcart) == 0 {
		return ctx.Wrap(shopcart)
	}

	var ids []int
	for _, item := range *shopcart {
		ids = append(ids, item.GoodsID)
		// ids += strconv.Itoa(item.GoodsID) + ","
	}
	strIds := strings.Trim(strings.Replace(fmt.Sprint(ids), " ", ",", -1), "[]")
	pRes, _ := util.Fetch(ctx, "GET", "product", "/apis/product/list/"+strIds+"/", nil)

	wellRes := model.ConvertToProductListResponse(pRes)

	for _, goods := range *shopcart {
		product := wellRes.Data.FindOne(goods.GoodsID)
		if product != nil {
			goods.Title = product.Title
			goods.Price = product.Price
			goods.CoverImgURL = product.CoverImgURL
		}
	}

	return model.Wrap(ctx, shopcart)
}

// ChangeCart godoc
// 更改购物车数量
func ChangeCart(c echo.Context) (err error) {
	ctx := c.(*model.UserContext)

	shopcartID := ctx.User.OpenID

	xmap := ctx.FormData
	cart, err2 := service.ChangeCart(shopcartID, *xmap)

	if err2 != nil {
		return ctx.WrapError(err2)
	}
	return ctx.Wrap(cart)

}
