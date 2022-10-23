const SudalFarm = artifacts.require("SudalFarm");
const SudalAuction = artifacts.require("SudalAuction");

module.exports = function (deployer) {
  deployer.deploy(SudalFarm);
  deployer.deploy(SudalAuction);
};
