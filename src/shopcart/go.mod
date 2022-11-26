module github.com/TencentCloudNative/microservices-demo/src/shopcart

go 1.15

require (
	github.com/alecthomas/template v0.0.0-20190718012654-fb15b899a751
	github.com/go-redis/redis/v7 v7.2.0
	github.com/go-yaml/yaml v2.1.0+incompatible // indirect
	github.com/golang/protobuf v1.4.2
	github.com/labstack/echo/v4 v4.2.2
	github.com/mitchellh/mapstructure v1.2.2
	github.com/sirupsen/logrus v1.7.0
	github.com/swaggo/swag v1.7.0
	golang.org/x/net v0.0.0-20201110031124-69a78807bb2b
	google.golang.org/grpc v1.34.0
	google.golang.org/protobuf v1.25.0
	shopcart v0.0.0-00010101000000-000000000000
)

replace shopcart => ./
