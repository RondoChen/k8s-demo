package model

import (
	"encoding/json"
	// "fmt"
)

// Product godoc
// 商品 对应本 service 的 Goods
type Product struct {
	ProductID   int     `json:"productId" xml:"productId"`
	Title       string  `json:"title" xml:"title"`
	Price       float32 `json:"tencentPrice" xml:"tencentPrice"`
	Subtitle    string  `json:"subtitle" xml:"subtitle"`
	CoverImgURL string  `json:"coverImgUrl" xml:"coverImgUrl"`
}

// ProductList godoc
// 商品 在分页中的商品列表
type ProductList struct {
	Items  []Product `json:"items" xml:"items"`
	Limit  int       `json:"limit" xml:"limit"`
	Offset int       `json:"offset" xml:"offset"`
	Total  int       `json:"total" xml:"total"`
}

// ProductListResponse godoc
// 商品 被response封装的
type ProductListResponse struct {
	Service   string      `json:"service" xml:"service"`
	Success   bool        `json:"success" xml:"success"`
	Timestamp int64       `json:"timestamp" xml:"timestamp"`
	Data      ProductList `json:"data" xml:"data"`
}

// Account godoc
// 账号系统
type Account struct {
	Mobile string `json:"mobile" xml:"mobile" mapstructure:"mobile"`
	Email  string `json:"email" xml:"email" mapstructure:"email"`
	Nick   string `json:"nick" xml:"nick" mapstructure:"nick"`
	OpenID string `json:"open_id" xml:"open_id" mapstructure:"open_id"`
	Avatar string `json:"avatar" xml:"avatar" mapstructure:"avatar"`
}

// AccountResponse godoc
// 账号系统的 封装
type AccountResponse struct {
	Service   string  `json:"service" xml:"service"`
	Success   bool    `json:"success" xml:"success"`
	Timestamp int64   `json:"timestamp" xml:"timestamp"`
	Data      Account `json:"data" xml:"data"`
}

// FindOne godoc
// 从商品列表里找到一个商品
func (items *ProductList) FindOne(id int) *Product {
	for _, product := range items.Items {
		if product.ProductID == id {
			return &product
		}
	}
	return nil
}

// ConvertToProductListResponse godoc
// 将字符串构造成为商品列表
func ConvertToProductListResponse(str string) ProductListResponse {
	if str == "" {
		panic("错误的字符串")
	}

	jsonBlob := []byte(str)
	var response ProductListResponse
	err := json.Unmarshal(jsonBlob, &response)
	if err != nil {
		panic(err)
	}

	return response
}

// ConvertToAccountResponse godoc
// 将字符串构造成为用户
func ConvertToAccountResponse(str string) AccountResponse {
	if str == "" {
		panic("错误的字符串")
	}

	jsonBlob := []byte(str)
	var response AccountResponse
	err := json.Unmarshal(jsonBlob, &response)
	if err != nil {
		panic(err)
	}

	return response
}
