package service

import (
	// "fmt"
	// "encoding/json"
	"errors"
	"shopcart/model"
	"shopcart/util"
	// "strconv"
)

func initCart(item map[string]interface{}) (model.Shopcart, error) {
	tarGoodsID := int(item["goods_id"].(float64))
	tarQuantity := int(item["quantity"].(float64))
	if tarGoodsID == 0 {
		return nil, errors.New("错误的商品ID")
	}
	if tarQuantity == 0 {
		return nil, errors.New("错误的商品数量")
	}

	return model.Shopcart{
		&model.ShopcartItem{
			GoodsID:  tarGoodsID,
			Quantity: tarQuantity,
		},
	}, nil
}

// MergeCart godoc
// 将商品加入购物车，如果发现相同商品，则合并商品数量
func MergeCart(cart *model.Shopcart, item map[string]interface{}) (bool, error) {
	tarGoodsID := int(item["goods_id"].(float64))
	tarQuantity := int(item["quantity"].(float64))
	if tarGoodsID == 0 {
		return false, errors.New("错误的商品ID")
	}
	if tarQuantity == 0 {
		return false, errors.New("错误的商品数量")
	}

	// var sItem model.ShopcartItem
	var repeated bool
	for _, s := range *cart {
		if s.GoodsID == tarGoodsID {
			s.Quantity += tarQuantity
			repeated = true
			break
		}
	}
	if !repeated {
		*cart = append(*cart, &model.ShopcartItem{
			GoodsID:  tarGoodsID,
			Quantity: tarQuantity,
		})
	}
	return true, nil
}

// AddCart godoc
// 将商品加入购物车并存储到 redis，如果发现相同商品，则合并商品数量
func AddCart(cartID string, item map[string]interface{}) (bool, error) {
	redisVal := util.ReadRedis(cartID)
	var shopcart model.Shopcart
	shopcart = model.ConvertToShopcart(redisVal)
	if shopcart == nil {
		shopcart0, err := initCart(item)
		if err != nil {
			return false, err
		}
		shopcart = shopcart0
	}
	_, err1 := MergeCart(&shopcart, item)

	// fmt.Println("this is cart after merge:", shopcart)

	if err1 != nil {
		return false, err1
	}
	_, err2 := util.WriteRedis(cartID, shopcart.String())
	if err2 != nil {
		return false, err2
	}
	return true, nil
}

// GetCart godoc
// 获取购物车
func GetCart(cartID string) *model.Shopcart {
	redisVal := util.ReadRedis(cartID)
	var shopcart model.Shopcart
	shopcart = model.ConvertToShopcart(redisVal)
	if shopcart == nil {
		return &model.Shopcart{}
	}

	return &shopcart
}

// ChangeCart godoc
// 修改购物车数量
func ChangeCart(cartID string, item map[string]interface{}) (*model.Shopcart, error) {
	tarGoodsID := int(item["goods_id"].(float64))
	tarQuantity := int(item["quantity"].(float64))
	if tarGoodsID == 0 {
		return nil, errors.New("错误的商品ID")
	}

	redisVal := util.ReadRedis(cartID)
	var shopcart model.Shopcart
	shopcart = model.ConvertToShopcart(redisVal)
	if shopcart == nil {
		return &model.Shopcart{}, nil
	}

	// fmt.Println("shopcart", shopcart, item)
	for index, sItem := range shopcart {
		// fmt.Println("shopcart", index, sItem)

		if sItem != nil && sItem.GoodsID == tarGoodsID {
			if tarQuantity <= 0 {
				shopcart[index] = shopcart[len(shopcart)-1]
				shopcart[len(shopcart)-1] = nil
				shopcart = shopcart[:len(shopcart)-1]
			} else {
				sItem.Quantity = tarQuantity
			}
		}
	}

	// fmt.Println("shopcart", shopcart, item)
	// var sItem model.ShopcartItem
	_, err2 := util.WriteRedis(cartID, shopcart.String())
	if err2 != nil {
		return nil, err2
	}

	return &shopcart, nil
}
