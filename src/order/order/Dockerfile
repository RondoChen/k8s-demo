#See https://aka.ms/containerfastmode to understand how Visual Studio uses this Dockerfile to build your images for faster debugging.

FROM mcr.microsoft.com/dotnet/core/sdk:3.1-buster AS build
WORKDIR /src
COPY ["./order.csproj", "order/"]
RUN dotnet restore "order/order.csproj"
WORKDIR "/src/order"
COPY . .
RUN dotnet build "order.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "order.csproj" -c Release -o /app/publish

FROM mcr.microsoft.com/dotnet/core/aspnet:3.1-buster-slim as final
WORKDIR /app
EXPOSE 7000
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "order.dll"]
