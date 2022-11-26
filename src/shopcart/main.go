package main

import (
	"fmt"
	"github.com/TencentCloudNative/microservices-demo/src/shopcart/config"
	"github.com/TencentCloudNative/microservices-demo/src/shopcart/controller"
	"github.com/TencentCloudNative/microservices-demo/src/shopcart/middleware"
	"github.com/labstack/echo/v4"
	"github.com/sirupsen/logrus"
	"os"
	"strconv"
	"time"
)

var log *logrus.Logger

func init() {
	log = logrus.New()
	log.Level = logrus.DebugLevel
	log.Formatter = &logrus.JSONFormatter{
		FieldMap: logrus.FieldMap{
			logrus.FieldKeyTime:  "timestamp",
			logrus.FieldKeyLevel: "severity",
			logrus.FieldKeyMsg:   "message",
		},
		TimestampFormat: time.RFC3339Nano,
	}
	log.Out = os.Stdout
}

func main() {
	e := echo.New()
	// e.Use(middleware.UserContextHandle)

	e.GET("/", controller.Home)
	ue := e.Group("/user", middleware.UserContextHandle)
	ue.POST("/shopcart", controller.AddGoods)
	ue.GET("/shopcart", controller.GetCart)
	ue.PUT("/shopcart", controller.ChangeCart)
	port := ":" + strconv.Itoa(config.AppConfig.Port)

	fmt.Println("Service", config.AppConfig.Name, "started. port", port)
	e.Logger.Fatal(e.Start(port))
}

// // server controls RPC service responses.
// type server struct{}

// //
// func (s *server) AddItem(context.Context, *pb.AddItemRequest) (*pb.Empty, error) {
// 	panic("implement me")
// }

// func (s *server) GetCart(context.Context, *pb.GetCartRequest) (*pb.Cart, error) {
// 	panic("implement me")
// }

// func (s *server) EmptyCart(context.Context, *pb.EmptyCartRequest) (*pb.Empty, error) {
// 	panic("implement me")
// }

// // Check is for health checking.
// func (s *server) Check(ctx context.Context, req *healthpb.HealthCheckRequest) (*healthpb.HealthCheckResponse, error) {
// 	return &healthpb.HealthCheckResponse{Status: healthpb.HealthCheckResponse_SERVING}, nil
// }

// func (s *server) Watch(req *healthpb.HealthCheckRequest, ws healthpb.Health_WatchServer) error {
// 	return status.Errorf(codes.Unimplemented, "health check via Watch not implemented")
// }
