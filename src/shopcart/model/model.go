package model

import (
	"encoding/json"
	// "fmt"
)

// ShopcartItem godoc
// 购物车商品
type ShopcartItem struct {
	GoodsID     int     `json:"goods_id" xml:"goods_id"`
	Title       string  `json:"title" xml:"title"`
	Quantity    int     `json:"quantity" xml:"quantity"`
	Price       float32 `json:"price" xml:"price"`
	CoverImgURL string  `json:"cover_img_url" xml:"cover_img_url"`
}

// Shopcart godoc
// 购物车
type Shopcart []*ShopcartItem

func (item ShopcartItem) String() string {

	out, err := json.Marshal(item)
	if err != nil {
		panic(err)
	}
	return string(out)
}

func (shopcart Shopcart) String() string {

	out, err := json.Marshal(shopcart)
	if err != nil {
		panic(err)
	}
	return string(out)
}

func ConvertToShopcart(str interface{}) Shopcart {
	if str == nil {
		return nil
	}

	jsonBlob := []byte(str.(string))
	var shopcart Shopcart
	err := json.Unmarshal(jsonBlob, &shopcart)
	if err != nil {
		panic(err)
	}

	return shopcart
}
