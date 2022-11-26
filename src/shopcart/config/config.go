package config

import (
	"io/ioutil"
	"os"

	"github.com/go-yaml/yaml"
)

type Configuration struct {
	Name      string                 `yaml:"name"`
	Port      int                    `yaml:"port"`
	GrpcPort  int                    `yaml:"grpcPort"`
	Redis     map[string]interface{} `yaml:"redis"`
	Providers map[string]string      `yaml:"providers"`
}

var AppConfig Configuration

func init() {
	// env := os.Getenv("run_env")
	// if env == "" {
	// 	env = "dev"
	// }
	// fmt.Println("Loading configuration. Env:", env)

	// file, err1 := os.Open("./config/" + env + ".json")
	// if err1 != nil {
	// 	panic(err1)
	// }
	// defer file.Close()

	confContent, err := ioutil.ReadFile("./config/config.yaml")
	if err != nil {
		panic(err)
	}
	confContent = []byte(os.ExpandEnv(string(confContent)))

	// decoder := json.NewDecoder(confContent)
	AppConfig = Configuration{}
	// err2 := decoder.Decode(&AppConfig)

	if err2 := yaml.Unmarshal(confContent, &AppConfig); err2 != nil {
		panic(err2)
	}
}
