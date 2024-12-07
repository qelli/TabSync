# XServerSync

So far this plugin uses `config.yml` to identify instances and connect to a redis host to be able to pub/sub to different events and keep all servers in sync regarding player lists and chats.


### Bugs & Features

- [x] Why is the initial sync request not sending sometimes?
- [x] Impl config on join/quit messages for players
- [x] Use ProtocolLib to add/remove players to tab based on each instance
- [x] Provide placeholders for PlaceholderAPI
- [x] TabAPI support
- [ ] Fix tablist order (waiting for ProtocolLib 5.4.0)
- [ ] Implement ChatSync
- [ ] Fix skin not showing on Tab for some players

### PlaceholderAPI

Available to use:

| Placeholder                      | Description                                         |
| -------------------------------- | --------------------------------------------------- |
| %xserversync_instancename%       | Name of the current instance                        |
| %xserversync_globalplayers%      | Amount of online players across all instances       |
| %xserversync_instanceplayers%    | Amount of players connected to the current instance |
