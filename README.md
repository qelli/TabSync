# XServerSync

So far this plugin uses `config.yml` to identify instances and connect to a redis host to be able to pub/sub to different events and keep all servers in sync regarding player lists and chats.


### Bugs & Features

- [x] Why is the initial sync request not sending sometimes?
- [x] Impl config on join/quit messages for players
- [x] Use ProtocolLib to add/remove players to tab based on each instance
- [ ] Implement ChatSync
- [ ] Fix skin not showing on Tab
- [ ] TabAPI support
