
module.exports = {
    index: (ctx) => {
        // console.log(ctx.headers);
        ctx.body = ("Hello, I am passport system.");
    },
    "/healthz/ready": ctx => {
        ctx.body = "I am ok.";
    }

}